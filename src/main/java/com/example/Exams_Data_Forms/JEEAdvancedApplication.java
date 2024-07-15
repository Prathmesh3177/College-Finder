package com.example.Exams_Data_Forms;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JEEAdvancedApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        Label preferredCourseLabel = new Label("Select Preferred Course For MHT CET");
        Button preferredCourseButton = new Button("BE/BTech");

        Label otherCourseLabel = new Label("Select Other Course For MHT CET");
        ComboBox<String> otherCourseComboBox = new ComboBox<>();
        otherCourseComboBox.setPromptText("-SELECT-");
        otherCourseComboBox.getItems().add("BE/BTech");

        Label examRankLabel = new Label("Enter Exam Rank/Score");
        Label examRankInfoLabel = new Label(
                "Giving your exam rank/score will help us recommend you better colleges and admissions. If you don't have actual rank/score, then enter expected rank/score.");
        Label mhtCetLabel = new Label("MHT CET (BE/BTech)");
        TextField examRankTextField = new TextField("83");

        Label genderLabel = new Label("Gender");
        RadioButton maleRadioButton = new RadioButton("Male");
        RadioButton femaleRadioButton = new RadioButton("Female");
        RadioButton otherRadioButton = new RadioButton("Other");
        ToggleGroup genderGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);
        otherRadioButton.setToggleGroup(genderGroup);

        Label categoryLabel = new Label("Category");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("-SELECT-");
        categoryComboBox.getItems().add("PWD");
        categoryComboBox.getItems().add("TFWS");
        categoryComboBox.getItems().add("EWS");
        categoryComboBox.getItems().add("Open");
        categoryComboBox.getItems().add("OBC");
        categoryComboBox.getItems().add("SC");
        categoryComboBox.getItems().add("ST");
        categoryComboBox.getItems().add("VJNT");

        Label domicileUniversityLabel = new Label("University");
        ComboBox<String> universityComboBox = new ComboBox<>();
        universityComboBox.setPromptText("-SELECT-");
        universityComboBox.getItems().add("HOME UNIVERSITY");
        universityComboBox.getItems().add("OTHER THAN HOME UNIVERSITY");

        Label preferableBranchLabel = new Label("Select Preferable Branch");
        ComboBox<String> branComboBox = new ComboBox<>();
        branComboBox.setPromptText("-SELECT-");
        branComboBox.getItems().add("CS");
        branComboBox.getItems().add("IT");
        branComboBox.getItems().add("E&TC");
        branComboBox.getItems().add("CIVIL");
        branComboBox.getItems().add("MECHANCAL");
        branComboBox.getItems().add("CHEMICAL");
        branComboBox.getItems().add("BIOTech.");

        // Create layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        gridPane.add(preferredCourseLabel, 0, 0);
        gridPane.add(preferredCourseButton, 1, 0);
        gridPane.add(otherCourseLabel, 0, 1);
        gridPane.add(otherCourseComboBox, 1, 1);
        gridPane.add(examRankLabel, 0, 2);
        gridPane.add(examRankInfoLabel, 0, 3, 2, 1);
        gridPane.add(mhtCetLabel, 0, 4);
        gridPane.add(examRankTextField, 1, 4);
        gridPane.add(genderLabel, 0, 5);

        HBox genderBox = new HBox(10);
        genderBox.getChildren().addAll(maleRadioButton, femaleRadioButton, otherRadioButton);
        gridPane.add(genderBox, 1, 5);

        gridPane.add(categoryLabel, 0, 6);
        gridPane.add(categoryComboBox, 1, 6);
        gridPane.add(domicileUniversityLabel, 0, 7);
        gridPane.add(universityComboBox, 1, 7);
        gridPane.add(preferableBranchLabel, 0, 8);
        gridPane.add(branComboBox, 1, 8);
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(gridPane, createNextButton());

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("MHT CET Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createNextButton() {
        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            
            Stage nextPage = new Stage();
            nextPage.setTitle("Next Page");
            Scene nextScene = new Scene(new Label("This is the next page."), 400, 200);
            nextPage.setScene(nextScene);
            nextPage.show();
        });
        return nextButton;
    }

}
