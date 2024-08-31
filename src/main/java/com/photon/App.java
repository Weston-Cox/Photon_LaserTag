package com.photon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;

import com.photon.DB.PostgreSQL;
import com.photon.UI.InitialScreenController;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static PostgreSQL postgreObj;

    @Override
    public void start(Stage stage) throws IOException {
        postgreObj = PostgreSQL.getInstance();
        scene = new Scene(loadFXML("InitialScreen"), 900, 720);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

    }

    @Override
    public void stop() {
        // Close PostgreSQL connection when the application stops
        try {
            if (postgreObj != null) {
                postgreObj.closeConnection();
                System.out.println("PostgreSQL connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory(param -> { // Dependency Injection of postgreSQL object
            if (param == InitialScreenController.class) {
                return new InitialScreenController(postgreObj);
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