package com.example;


import com.example.HomePage.CollegePredictorHomePage;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

public class Splash {
    private Stage primaryStage;
    private Scene scene;

    public Splash(Stage primaryStage){
        this.primaryStage = primaryStage;
        initScenes();
    }
    public void setPrimaryStage(Scene scene) {
        if (primaryStage != null) {
            primaryStage.setScene(scene);
        } else {
            System.err.println("Primary stage is not set. Cannot set scene.");
        }
    }
    private void initScenes() {
        initSplashScene();
    }


    
    public void initSplashScene() {
        // Load image
        Image image = new Image(getClass().getResourceAsStream("/Images/SplashScreen/Splash.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1450);
        imageView.setFitHeight(800);
        imageView.setPreserveRatio(false);

        StackPane root = new StackPane(imageView);
         scene = new Scene(root, 1800, 800);

        primaryStage.setScene(scene);
        primaryStage.setWidth(1800);
        primaryStage.setHeight(800);
        primaryStage.show();
        
        // Fade animation
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3.0), imageView);
        fadeTransition.setFromValue(0.5);
        fadeTransition.setToValue(1.0);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setCycleCount(2);

        // Pause before transitioning to the next screen
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            primaryStage.hide();
             CollegePredictorHomePage collegePredictorHomePage = new CollegePredictorHomePage(primaryStage);
            primaryStage.setScene(collegePredictorHomePage.getHomePageScene());
            primaryStage.setTitle("Home Page");
            primaryStage.show();
        });

        
        fadeTransition.play();
        pause.play();
    }
    public Scene getSplashScene() {
        return scene;
    }

   
}
