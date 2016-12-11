package sample;

import Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception{
        try {
            Controller.closeConnection();
            System.out.println("Connection closed!");
        }catch (Exception e){
            System.out.println("No connection as of yet");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
