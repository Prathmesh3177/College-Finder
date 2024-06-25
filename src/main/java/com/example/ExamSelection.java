package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.*;

public class ExamSelection {
    private BorderPane root;
   

    private String selectedExam = "B.Tech";

    public VBox getExamSelectionLayout(Stage primaryStage ) {
       
        Label title = new Label("Select a Course");
        title.setFont(new Font("Arial", 24));
        title.setTextFill(Color.TEAL);

        Label selectExamLabel = new Label("Select Exam");


      
        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            System.out.println("Selected Exam: " + selectedExam);
            // Call MHTCETApplication to show the next layout
            MHTCETApplication mhtCetApplication = new MHTCETApplication();
            Stage mhtCetStage = new Stage();
            try {
                mhtCetApplication.start(mhtCetStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            primaryStage.close(); // Close the current window
        });

    
        Image jeeMainImage = new Image(getClass().getResourceAsStream("/BtechExams/JeeMain.jpg"));
        Image jeeAdvancedImage = new Image(getClass().getResourceAsStream("/BtechExams/JeeAdav.png"));
        Image mhtCetImage = new Image(getClass().getResourceAsStream("/BtechExams/cet.jpeg"));

      
        ImageView jeeMainImageView = new ImageView(jeeMainImage);
        jeeMainImageView.setFitWidth(150);
        jeeMainImageView.setFitHeight(150);

        ImageView jeeAdvancedImageView = new ImageView(jeeAdvancedImage);
        jeeAdvancedImageView.setFitWidth(150);
        jeeAdvancedImageView.setFitHeight(150);

        ImageView mhtCetImageView = new ImageView(mhtCetImage);
        mhtCetImageView.setFitWidth(150);
        mhtCetImageView.setFitHeight(150);

       
        Label jeeMainLabel = new Label("JEE Main");
        Label jeeAdvancedLabel = new Label("JEE Advanced");
        Label mhtCetLabel = new Label("MHT CET");

       
        RadioButton jeeMainRadio = new RadioButton();
        RadioButton jeeAdvancedRadio = new RadioButton();
        RadioButton mhtCetRadio = new RadioButton();
        ToggleGroup examGroup = new ToggleGroup();
        jeeMainRadio.setToggleGroup(examGroup);
        jeeAdvancedRadio.setToggleGroup(examGroup);
        mhtCetRadio.setToggleGroup(examGroup);

        // Set actions for image clicks
        jeeMainImageView.setOnMouseClicked(event -> {
            jeeMainRadio.setSelected(true);
            selectedExam = "JEE Main";
        });
        jeeAdvancedImageView.setOnMouseClicked(event -> {
            jeeAdvancedRadio.setSelected(true);
            selectedExam = "JEE Advanced";
        });
        mhtCetImageView.setOnMouseClicked(event -> {
            mhtCetRadio.setSelected(true);
            selectedExam = "MHT CET";
        });

        // Create GridPane for exam images and labels
        GridPane examGrid = new GridPane();
        examGrid.setHgap(40);
        examGrid.setVgap(10);
        examGrid.setAlignment(Pos.CENTER);
        examGrid.setPadding(new Insets(20, 20, 20, 20));
        examGrid.add(jeeMainImageView, 0, 0);
        examGrid.add(jeeMainLabel, 0, 1);
        examGrid.add(jeeMainRadio, 0, 2);
        examGrid.add(jeeAdvancedImageView, 1, 0);
        examGrid.add(jeeAdvancedLabel, 1, 1);
        examGrid.add(jeeAdvancedRadio, 1, 2);
        examGrid.add(mhtCetImageView, 2, 0);
        examGrid.add(mhtCetLabel, 2, 1);
        examGrid.add(mhtCetRadio, 2, 2);

       
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 20, 20, 20));
        buttonBox.getChildren().add(nextButton);

        
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20, 20, 20, 20));
        root.getChildren().addAll(title, selectExamLabel, examGrid, buttonBox);

        return root;
    }
  
}
