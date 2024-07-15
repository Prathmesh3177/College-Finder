package com.example.Login.initialize;

import com.example.Login.controller.LoginController;

import javafx.application.Application;
import javafx.stage.Stage;

public class InitializeApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginController loginController = new LoginController(primaryStage);
        primaryStage.setScene(loginController.getLoginScene());
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

   
    
}
