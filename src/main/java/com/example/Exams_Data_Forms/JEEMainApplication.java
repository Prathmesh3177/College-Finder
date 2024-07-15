package com.example.Exams_Data_Forms;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class JEEMainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JEE Main Application Form");

        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        
        grid.add(new Label("Name:"), 0, 0);
        TextField nameField = new TextField();
        grid.add(nameField, 1, 0);

        grid.add(new Label("Date of Birth:"), 0, 1);
        DatePicker dobPicker = new DatePicker();
        grid.add(dobPicker, 1, 1);

        grid.add(new Label("Gender:"), 0, 2);
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Other");
        grid.add(genderComboBox, 1, 2);

        grid.add(new Label("Email:"), 0, 3);
        TextField emailField = new TextField();
        grid.add(emailField, 1, 3);

        grid.add(new Label("Phone Number:"), 0, 4);
        TextField phoneField = new TextField();
        grid.add(phoneField, 1, 4);

        
        grid.add(new Label("School Name:"), 0, 5);
        TextField schoolField = new TextField();
        grid.add(schoolField, 1, 5);

        grid.add(new Label("Board:"), 0, 6);
        ComboBox<String> boardComboBox = new ComboBox<>();
        boardComboBox.getItems().addAll("CBSE", "ICSE", "State Board", "Other");
        grid.add(boardComboBox, 1, 6);

        grid.add(new Label("Year of Passing:"), 0, 7);
        TextField yearField = new TextField();
        grid.add(yearField, 1, 7);

        grid.add(new Label("Roll Number:"), 0, 8);
        TextField rollField = new TextField();
        grid.add(rollField, 1, 8);

        
        grid.add(new Label("College Location Preference:"), 0, 9);
        ComboBox<String> centerComboBox = new ComboBox<>();
        centerComboBox.getItems().addAll("Delhi", "Mumbai", "Chennai", "Kolkata", "Bangalore");
        grid.add(centerComboBox, 1, 9);

        grid.add(new Label("Category:"), 0, 10);
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("General", "OBC", "SC", "ST");
        grid.add(categoryComboBox, 1, 10);

        grid.add(new Label("Special Reservation:"), 0, 11);
        ComboBox<String> reservationComboBox = new ComboBox<>();
        reservationComboBox.getItems().addAll("None", "PwD", "Defense");
        grid.add(reservationComboBox, 1, 11);

        
        Button submitButton = new Button("Submit");
        grid.add(submitButton, 1, 12);
        GridPane.setMargin(submitButton, new Insets(10, 0, 0, 0));

        
        submitButton.setOnAction(e -> handleSubmit(
            nameField.getText(), dobPicker.getValue(), genderComboBox.getValue(),
            emailField.getText(), phoneField.getText(), schoolField.getText(),
            boardComboBox.getValue(), yearField.getText(), rollField.getText(),
            centerComboBox.getValue(), categoryComboBox.getValue(), reservationComboBox.getValue()
        ));

        
        Scene scene = new Scene(grid, 400, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleSubmit(String name, java.time.LocalDate dob, String gender, String email, String phone,
                              String school, String board, String year, String roll, String center,
                              String category, String reservation) {
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Form Submission");
        alert.setHeaderText(null);
        alert.setContentText("Form submitted successfully!\n" +
            "Name: " + name + "\n" +
            "Date of Birth: " + dob + "\n" +
            "Gender: " + gender + "\n" +
            "Email: " + email + "\n" +
            "Phone: " + phone + "\n" +
            "School: " + school + "\n" +
            "Board: " + board + "\n" +
            "Year of Passing: " + year + "\n" +
            "Roll Number: " + roll + "\n" +
            "Exam Center Preference: " + center + "\n" +
            "Category: " + category + "\n" +
            "Special Reservation: " + reservation);

        alert.showAndWait();
    }

    
}


























