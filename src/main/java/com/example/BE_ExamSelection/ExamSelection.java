package com.example.BE_ExamSelection;

import com.example.CET_SearchPage.SearchPage;
import com.example.Exams_Data_Forms.JEEAdvancedApplication;
import com.example.Exams_Data_Forms.JEEMainApplication;
import com.example.Exams_Data_Forms.MHTCETApplication;
import com.example.HomePage.CollegePredictorHomePage;
import com.example.Login.firebaseConfig.DataService;
import com.example.Login.initialize.InitializeApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ExamSelection {
    private Stage primaryStage;
    private BorderPane root;
    private Scene scene;
    private CollegePredictorHomePage collegePredictorHomePage;
    private String selectedExam = "B.Tech";

    public ExamSelection(CollegePredictorHomePage collegePredictorHomePage) {
        this.collegePredictorHomePage = collegePredictorHomePage;
        this.root = new BorderPane();
        Image backgroundImage = new Image(getClass().getResourceAsStream("/Images/BtechExams/ExamPage.jpg"));

        BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1200, 800, true, true, true, true));
        Background background = new Background(backgroundImageObject);
        root.setBackground(background);
    }

    public Scene createExamSelectionScene(Stage primaryStage) {
        this.primaryStage = primaryStage;  // Assign the primaryStage

        HBox topBar = createTopBar();
        root.setTop(topBar);

        VBox mainLayout = createMainLayout();
        VBox.setMargin(mainLayout, new Insets(30, 0, 0, 0)); // Add space between top bar and main layout
        root.setCenter(mainLayout);

        return new Scene(root, 1800, 800);
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(300);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));

        // Create the logo
        Image logoImage = new Image(getClass().getResourceAsStream("/Images/Logo.png")); // replace with your logo image
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(150);
        logoImageView.setFitHeight(100);
        logoImageView.setPreserveRatio(true);

        Label lb = new Label("College Finder");
        lb.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lb.setTextFill(Color.WHITE);

        VBox vb = new VBox(10, logoImageView, lb);
        vb.setPadding(new Insets(0, 0, 0, 20));

        // Create the search field
        TextField searchField = new TextField();
        searchField.setPromptText("Search..");
        searchField.setStyle("-fx-background-color: transparent;");
        searchField.setPrefWidth(500);
        searchField.setPrefHeight(20);

        // Add search logo
        Image searchIcon = new Image(getClass().getResourceAsStream("/Images/search.png")); // replace with your icon image
        ImageView searchImageView = new ImageView(searchIcon);
        searchImageView.setFitWidth(20);
        searchImageView.setFitHeight(20);

        // Create an HBox to layout the icon and the text field
        HBox hbox = new HBox(5); // 5 is the spacing between the icon and the text field
        hbox.getChildren().addAll(searchImageView, searchField);
        hbox.setPadding(new Insets(10, 0, 0, 0));

        // Add some padding to the left of the text field
        searchField.setPadding(new Insets(0, 5, 0, 0)); // adjust the values as needed
        searchField.setStyle("-fx-background-color: transparent;");

        // Increase size on hover
        searchField.setOnMouseEntered(event -> {
            searchField.setPrefWidth(600);
        });

        searchField.setOnMouseExited(event -> {
            searchField.setPrefWidth(500);
        });

        HBox imgser = new HBox(70, vb, hbox);

        // Add CSS to style the search field with rounded corners
        searchField.setStyle("-fx-background-radius: 10; -fx-border-width: 1; -fx-border-radius: 10;");

        // Create the navigation links
        Button homeButton = new Button("Home");
        homeButton.setStyle("-fx-background-color: transparent;");
        homeButton.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        homeButton.setOnAction(event -> collegePredictorHomePage.showHomePage());

        
        Button aboutButton = new Button("About");
        aboutButton.setStyle("-fx-background-color: transparent;");
        aboutButton.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        Button coursesButton = new Button("Courses");
        coursesButton.setStyle("-fx-background-color: transparent;");
        coursesButton.setFont(Font.font("Arial", FontWeight.BOLD, 17));
       
        Button loginButton = new Button("Login");
       
        loginButton.setOnAction(event -> {
            InitializeApp initializeApp = new InitializeApp();
            Stage inStage = new Stage();
            try {
                initializeApp.start(inStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        HBox buttonBox = new HBox(10, homeButton, aboutButton, coursesButton, loginButton);
        topBar.getChildren().addAll(imgser, buttonBox);

        return topBar;
    }

    private VBox createMainLayout() {
        Label title = new Label("Select a Exam");
        title.setFont(new Font("Arial", 40));
        title.setPadding(new Insets(10, 5, 10, 230));
        title.setTextFill(Color.BLACK);

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            System.out.println("Selected Exam: " + selectedExam);
            switch (selectedExam) {
                case "JEE Main":
                    openJEEMainForm(primaryStage);
                    break;
                case "JEE Advanced":
                    openJEEAdvancedForm(primaryStage);
                    break;
                case "MHT CET":
                    openMHTCETForm(primaryStage);
                    break;
                default:
                    System.out.println("No exam selected");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> collegePredictorHomePage.showHomePage());

        Image jeeMainImage = new Image(getClass().getResourceAsStream("/Images/BtechExams/JeeMain.jpg"));
        Image jeeAdvancedImage = new Image(getClass().getResourceAsStream("/Images/BtechExams/JeeAdav.png"));
        Image mhtCetImage = new Image(getClass().getResourceAsStream("/Images/BtechExams/cet.jpeg"));

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

        GridPane examGrid = new GridPane();
        examGrid.setHgap(40);
        examGrid.setVgap(10);
        examGrid.setPadding(new Insets(10, 5, 10, 50));
        examGrid.add(jeeMainImageView, 0, 0);
        examGrid.add(jeeMainLabel, 0, 1);
        examGrid.add(jeeMainRadio, 0, 2);
        examGrid.add(jeeAdvancedImageView, 1, 0);
        examGrid.add(jeeAdvancedLabel, 1, 1);
        examGrid.add(jeeAdvancedRadio, 1, 2);
        examGrid.add(mhtCetImageView, 2, 0);
        examGrid.add(mhtCetLabel, 2, 1);
        examGrid.add(mhtCetRadio, 2, 2);

        HBox buttonBox = new HBox(20);
        buttonBox.setPadding(new Insets(10, 5, 10, 230));
        buttonBox.getChildren().addAll(backButton, nextButton);

        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(title, examGrid, buttonBox);
        mainLayout.setPadding(new Insets(10, 5, 10, 50));

        return mainLayout;
    }

    private void openMHTCETForm(Stage primaryStage) {
        DataService dataService = new DataService();
        if (primaryStage == null) {
            System.out.println("Error: primaryStage is null.");
            return;
        }
        
        SearchPage searchPage;
        try {
            searchPage = new SearchPage("MHT-CET",dataService,collegePredictorHomePage);
        } catch (Exception e) {
            System.out.println("Error: Unable to create SearchPage. " + e.getMessage());
            e.printStackTrace();
            return;
        }

        if (searchPage == null) {
            System.out.println("Error: searchPage is null.");
            return;
        }

        Scene searchScene;
        try {
            searchScene = searchPage.createSearchScene(primaryStage);
        } catch (Exception e) {
            System.out.println("Error: Unable to create searchScene. " + e.getMessage());
            e.printStackTrace();
            return;
        }

        if (searchScene == null) {
            System.out.println("Error: searchScene is null.");
            return;
        }

        primaryStage.setScene(searchScene);
    }

    private void openJEEMainForm(Stage primaryStage) {
        JEEMainApplication jeeMainApplication = new JEEMainApplication();
        Stage jeeMainStage = new Stage();
        try {
            jeeMainApplication.start(jeeMainStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        primaryStage.close();
    }

    private void openJEEAdvancedForm(Stage primaryStage) {
        JEEAdvancedApplication jeeAdvancedApplication = new JEEAdvancedApplication();
        Stage jeeAdvancedStage = new Stage();
        try {
            jeeAdvancedApplication.start(jeeAdvancedStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        primaryStage.close();
    }
    
}
