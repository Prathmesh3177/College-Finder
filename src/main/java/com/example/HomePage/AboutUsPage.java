package com.example.HomePage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AboutUsPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the main container
        StackPane root = new StackPane();
        root.setPadding(new Insets(20));

        // Create the header
        Label title = new Label("About Us");
        title.setFont(new Font("Arial", 30));
        title.setTextFill(Color.BLACK);

        // Create the content
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);

        // Create the team section
        Label teamTitle = new Label("Meet the team");
        teamTitle.setFont(new Font("Arial", 24));
        teamTitle.setTextFill(Color.BLACK);

        Label teamDescription = new Label("The most passionate, devoted, and talented people you have ever met.");
        teamDescription.setFont(new Font("Arial", 18));
        teamDescription.setTextFill(Color.BLACK);

        // Load team member images from local file system
        Image member1Image = new Image(getClass().getResourceAsStream("/Images/BtechExams/JeeMain.jpg"));
        ImageView member1 = new ImageView(member1Image);
        member1.setFitWidth(100);
        member1.setFitHeight(100);
        member1.setPreserveRatio(true);

        Image member2Image = new Image(getClass().getResourceAsStream("/Images/BtechExams/JeeMain.jpg"));
        ImageView member2 = new ImageView(member2Image);
        member2.setFitWidth(100);
        member2.setFitHeight(100);
        member2.setPreserveRatio(true);

        Image member3Image = new Image(getClass().getResourceAsStream("/Images/BtechExams/JeeMain.jpg"));
        ImageView member3 = new ImageView(member3Image);
        member3.setFitWidth(100);
        member3.setFitHeight(100);
        member3.setPreserveRatio(true);

        // Add content to the VBox
        content.getChildren().addAll(title, teamTitle, teamDescription, member1, member2, member3);

        // Add content to the StackPane
        root.getChildren().add(content);

        // Create the scene and show the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("About Us");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}