package com.example.Login.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.Login.firebaseConfig.DataService;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignupController {

    private LoginController loginController;

    public SignupController(LoginController loginController) {
        this.loginController = loginController;
    }

    public Scene createSignupScene(Stage primaryStage) {
        // Labels and TextFields for user input
        Label userLabel = new Label("Username:");
        userLabel.setStyle("-fx-font-weight:bold;-fx-font-size:16;");
        TextField userTextField = new TextField();

        Label passLabel = new Label("Password:");
        passLabel.setStyle("-fx-font-weight:bold;-fx-font-size:16;");
        PasswordField passField = new PasswordField();

        // Role selection using RadioButtons
        Label roleLabel = new Label("SignUp as:");
        roleLabel.setStyle("-fx-font-weight:bold;-fx-font-size:16;");
        RadioButton studentRadioButton = new RadioButton("Student");
        studentRadioButton.setStyle("-fx-font-size:14;");
        RadioButton collegeRadioButton = new RadioButton("College");
        collegeRadioButton.setStyle("-fx-font-size:14;");
        ToggleGroup roleToggleGroup = new ToggleGroup();
        studentRadioButton.setToggleGroup(roleToggleGroup);
        collegeRadioButton.setToggleGroup(roleToggleGroup);

        // Buttons for signup and back to login
        Button signupButton = new Button("Signup");
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> loginController.showLoginScene());

        // Layout setup for fields and buttons
        VBox fieldBox1 = new VBox(10, userLabel, userTextField);
        fieldBox1.setMaxSize(300, 30);

        VBox fieldBox2 = new VBox(10, passLabel, passField);
        fieldBox2.setMaxSize(300, 30);

        HBox roleBox = new HBox(20, studentRadioButton, collegeRadioButton);
        roleBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(50, signupButton, backButton);
        buttonBox.setMaxSize(350, 30);
        buttonBox.setAlignment(Pos.CENTER);

        // Handle signup button action
        signupButton.setOnAction(event -> {
            RadioButton selectedRadioButton = (RadioButton) roleToggleGroup.getSelectedToggle();
            String role = selectedRadioButton != null ? selectedRadioButton.getText() : null;
            handleSignup(primaryStage, userTextField.getText(), passField.getText(), role);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setContentText("SignUp Successfully !");
            alert.setHeaderText(null);
            alert.showAndWait();

        });

        // Main container VBox for the scene
        VBox vbox = new VBox(20, fieldBox1, fieldBox2, roleLabel, roleBox, buttonBox);
        vbox.setAlignment(Pos.CENTER);

        Image backgroundImage = new Image(getClass().getResource("/Images/LoginPage/LoginPage.jpg").toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1000);
        backgroundImageView.setFitHeight(600);
        backgroundImageView.setPreserveRatio(true);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImageView, vbox);

        return new Scene(stackPane, 1000, 600);

    }

    private void handleSignup(Stage primaryStage, String username, String password, String role) {

        DataService dataService;
        try {
            // Initialize DataService to interact with Firestore or Firebase
            dataService = new DataService();

            // Create a Map to store user data
            Map<String, Object> data = new HashMap<>();
            data.put("password", password);
            data.put("username", username);
            data.put("role", role);

            // Add user data to the Firestore collection 'users'
            dataService.addData("users", username, data);
            System.out.println("User registered successfully");

            // Show login scene after successful signup
            loginController.showLoginScene();
        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle exceptions, such as Firebase initialization or data insertion errors
        }
    }
}
