package com.example.Login.controller;

import java.util.concurrent.ExecutionException;

import com.example.Login.dashboards.CollegePage;
import com.example.Login.dashboards.StudentPage;
import com.example.Login.firebaseConfig.DataService;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {

    private Stage primaryStage;
    private Scene loginScene;
    private Scene userScene;
    private Scene adminScene;
    private DataService dataService;
    public static String key;

    public LoginController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        dataService = new DataService();
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
        initLoginScene();
    }

    private void initLoginScene() {
        Label userLabel = new Label("Username");
        TextField userTextField = new TextField();
        Label passLabel = new Label("Password");
        PasswordField passField = new PasswordField();
        Label loginasLabel = new Label("Login as");
        loginasLabel.setMaxSize(300, 30);
        loginasLabel.setStyle("-fx-text-fill: black;-fx-font-weight:bold;-fx-font-size:12;");
        ComboBox<String> loginasComboBox = new ComboBox<>();
        loginasComboBox.getItems().addAll("Student", "College");
        loginasComboBox.setMaxSize(300, 30);

        // Button adminLoginButton = new Button("College Login");
        // Button userLoginButton = new Button("Student Login");
        Button loginButton = new Button("Login");
        loginButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String username = userTextField.getText();
                String password = passField.getText();
                String loginAs = loginasComboBox.getValue();
                userTextField.clear();
                passField.clear();

                if ("Student".equals(loginAs)) {
                    handleUserLogin(username, password);
                } else {
                    handleAdminLogin(username, password);
                }
            }

        });
        Button signupButton = new Button("Signup");
        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSignupScene();
                userTextField.clear();
                passField.clear();
            }
        });

        userLabel.setStyle(
                "-fx-text-fill: black;-fx-font-weight:bold;-fx-font-size:12;-fx-max-width:200;-fx-max-height:50;");
        passLabel.setStyle(
                "-fx-text-fill: black;-fx-font-weight:bold;-fx-font-size:12;-fx-max-width:200;-fx-max-height:50;");

        VBox fieldBox1 = new VBox(10, userLabel, userTextField);
        fieldBox1.setMaxSize(300, 30);
        VBox fieldBox2 = new VBox(10, passLabel, passField);
        fieldBox2.setMaxSize(300, 30);
        HBox buttonBox = new HBox(50, loginButton, signupButton);
        buttonBox.setMaxSize(350, 30);
        buttonBox.setAlignment(Pos.CENTER);

        userTextField.setStyle("-fx-set-pref-width:350");
        passField.setStyle("-fx-set-pref-width:350");

        VBox vbox = new VBox(20, loginasLabel, loginasComboBox, fieldBox1, fieldBox2, buttonBox);
        // vbox.setStyle("-fx-background-image:url('https://static.vecteezy.com/system/resources/thumbnails/007/611/491/small/graduation-hats-background-with-mortar-boards-vector.jpg')");
        // vbox.setStyle("-fx-background-image: url('https://static.vecteezy.com/sy);");
        vbox.setAlignment(Pos.CENTER);

        Image backgroundImage = new Image(getClass().getResource("/Images/LoginPage/LoginPage.jpg").toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1000);
        backgroundImageView.setFitHeight(600);
        backgroundImageView.setPreserveRatio(true);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImageView, vbox);

        loginScene = new Scene(stackPane, 600, 600);
    }

    private void initUserScene() {
        StudentPage userPage = new StudentPage(dataService);
        userScene = new Scene(userPage.createStudentScene(this::handleLogout), 700, 700);
    }

    private void initAdminScene() {
        CollegePage collegePage = new CollegePage(this, dataService);
        adminScene = new Scene(collegePage.createCollegeDashboard(this::handleLogout), 700, 1000);

    }

    public Scene getLoginScene() {
        return loginScene;
    }

    public void showLoginScene() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);
        primaryStage.show();
    }

    private void handleUserLogin(String username, String password) {
        try {
            if (dataService.authenticateUser(username, password) && !dataService.isAdmin(username)) {
                key = username;
                initUserScene();
                primaryStage.setScene(userScene);
                primaryStage.setTitle("User Dashboard");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Credentials");
                alert.setContentText("Wrong Username or Password !");
                alert.setHeaderText(null);

                alert.showAndWait();
                System.out.println("Invalid client credentials");
            }
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void handleLogin(String username, String password) {
        try {
            if (dataService.authenticateUser(username, password)) {
                key = username;
                initUserScene();
                primaryStage.setScene(userScene);
                primaryStage.setTitle("User Dashboard");
            } else {
                System.out.println("Invalid credentials");
                key = null;
            }
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void handleAdminLogin(String username, String password) {
        try {
            if (dataService.authenticateUser(username, password) && dataService.isAdmin(username)) {
                initAdminScene();
                primaryStage.setScene(adminScene);
                primaryStage.setTitle("Admin Dashboard");
            } else {
                System.out.println("Invalid admin credentials");
            }
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void showSignupScene() {
        SignupController signupController = new SignupController(this);
        Scene signupScene = signupController.createSignupScene(primaryStage);
        primaryStage.setScene(signupScene);
        primaryStage.setTitle("Signup");
        primaryStage.show();
    }

    private void handleLogout() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
    }

    public void returnToAdminView() {
        primaryStage.setScene(adminScene);
        primaryStage.setTitle("Admin Dashboard");
    }
}
