package com.photon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;

import com.photon.DB.PostgreSQL;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private PostgreSQL postgreObj;

    @Override
    public void start(Stage stage) throws IOException {
        // postgreObj = PostgreSQL.getInstance();
        scene = new Scene(loadFXML("InitialScreen"), 900, 720);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

        // Add shutdown hook to close PostgreSQL connection
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (postgreObj != null && postgreObj.getConnection() != null && !postgreObj.getConnection().isClosed()) {
                    postgreObj.closeConnection();
                    System.out.println("PostgreSQL connection closed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
    }

    // 
    // public void stop() {
        // Close PostgreSQL connection when the application stops
        // postgreObj.closeConnection();
        // try {
        //     if (postgreObj != null && postgreObj.getConnection() != null && !postgreObj.getConnection().isClosed()) {
        //         postgreObj.closeConnection();
        //         System.out.println("PostgreSQL connection closed.");
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // } 
    // }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}