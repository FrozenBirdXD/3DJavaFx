package com.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartPage extends Application {
    private static Scene scene;
    private static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        StartPage.stage = stage;

        // read user settings
        SettingsHandler.readSettings();

        scene = new Scene(loadFXML("startMenu"), 1440, 800);

        stage.setTitle("Flight Simulator");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void closeStage() {
        stage.close();
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public static void setScene(String fxml) throws IOException {
        Scene scene = new Scene(loadFXML(fxml));
        stage.setScene(scene);
    }

    public static void setSettingsScene(String fxml, boolean cameFromStartMenu) throws IOException {
        FXMLLoader loader = new FXMLLoader(StartPage.class.getResource("/fxml/" + fxml + ".fxml"));
        loader.setController(new SettingsController(cameFromStartMenu));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    // static void setRoot(String fxml) throws IOException {
    // scene.setRoot(loadFXML(fxml));
    // }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartPage.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
