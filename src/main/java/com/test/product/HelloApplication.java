package com.test.product;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("My products");
        stage.setScene(new Scene(new FXMLLoader(HelloApplication.class.getResource("main-view.fxml")).load()));
        stage.show();
    }
}