package com.photon;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import com.photon.DB.PostgreSQL;
import com.photon.Helpers.GameTimer;
import com.photon.UDP.UDPClient;
import com.photon.UDP.UDPServer;
import com.photon.UI.InitialScreenController;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    private static Scene scene;
    private static PostgreSQL postgreObj;
    private static GameTimer gameTimer;
    private static UDPClient udpClient;
    private static UDPServer udpServer;
    private Thread udpServerThread;
    private MediaPlayer gameSound;




    @Override
    public void start(Stage stage) throws IOException {
        udpClient = new UDPClient();
        udpServer = new UDPServer(7501, message -> {
            // Temporary Callback
        });
        postgreObj = PostgreSQL.getInstance();
        gameTimer = new GameTimer();

        // Create the MediaPlayer instance
        Random rand = new Random();
        int randNum = rand.nextInt(1,8);
        Media media = new Media(getClass().getResource("/tracks/Track0" + randNum + ".mp3").toString());
        gameSound = new MediaPlayer(media);
        gameTimer.setMediaPlayer(gameSound);

        // Start the UDP server in a new thread
        udpServerThread = new Thread(udpServer);
        udpServerThread.start();

        // Load the splash screen
        Parent splashScreen = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        Scene splashScene = new Scene(splashScreen, 900, 720);
        stage.setScene(splashScene);
        stage.show();

        // Create a PauseTransition to delay the start of the fade transition
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
        pauseTransition.setOnFinished(event -> {
            // Create the fade transition for the splash screen
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), splashScreen);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setOnFinished(e -> {
                try {
                    // Load the InitialScreen
                    Parent initialScreen = loadFXML("InitialScreen");
                    scene = new Scene(initialScreen, 900, 720);
                    // scene.getStylesheets().add(getClass().getResource("/com/photon/PhotonFX.css").toExternalForm());

                    // Set the initial opacity of the InitialScreen to 0.0
                    initialScreen.setOpacity(0.0);
                    stage.setScene(scene);
                    stage.setTitle("Initial Screen");
                    stage.setResizable(true);

                    // Create the fade transition for the InitialScreen
                    FadeTransition fadeInTransition = new FadeTransition(Duration.seconds(3), initialScreen);
                    fadeInTransition.setFromValue(0.0);
                    fadeInTransition.setToValue(1.0);
                    fadeInTransition.play();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            fadeTransition.play();
        });
        pauseTransition.play();
    }

    @Override
    public void stop() {
        System.out.println("Application is closing...");
        gameTimer.stopCountdown();
        // Close PostgreSQL connection when the application stops
        try {
            if (postgreObj != null) {
                postgreObj.closeConnection();
                System.out.println("PostgreSQL connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        try {
            if (udpClient != null) {
                udpClient.send("221");
                udpClient.close();
                System.out.println("UDP connection closed.");
            }
        } catch (IOException e) {
            System.out.println("Error sending game over signal to server: App\n" + e);
        }

        if (udpServer != null) {
            udpServer.stop();
            System.out.println("UDP Server stopped.");
        }

        if (udpServerThread != null) {
            udpServerThread.interrupt();
            try {
                udpServerThread.join();
                System.out.println("UDP Server thread joined.");
            } catch (InterruptedException e) {
                System.out.println("Error joining UDP Server thread: App\n" + e);
            }
        }

        // Ensure the MediaPlayer is properly stopped and disposed of
        if (gameSound != null) {
            
        }


        // Ensure all JavaFX threads are closed
        Platform.exit();

        // Forcefully exit the application
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Wait for 2 seconds to allow resources to be disposed of
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0); // Forcefully exit the application
        }).start();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory(param -> { // Dependency Injection of postgreSQL object
            if (param == InitialScreenController.class) {
                return new InitialScreenController(postgreObj, udpClient, udpServer, gameTimer);
            } else {
                try {
                    return param.getConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return fxmlLoader.load();
    }

    
    public static void main(String[] args) {
        launch();
    }

}