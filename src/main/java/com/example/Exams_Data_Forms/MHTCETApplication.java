package com.example.Exams_Data_Forms;

import com.example.BE_ExamSelection.ExamSelection;
import com.example.HomePage.CollegePredictorHomePage;
import com.example.Login.initialize.InitializeApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class MHTCETApplication {
    private Stage  primaryStage;
    private BorderPane root;
    private CollegePredictorHomePage collegePredictorHomePage;

    private ExamSelection examSelection;
    private String selectedExam = "CET";

    public MHTCETApplication(CollegePredictorHomePage collegePredictorHomePage) {
        this.collegePredictorHomePage = collegePredictorHomePage;
        this.root = new BorderPane();
         Image backgroundImage = new Image(getClass().getResourceAsStream("/ExamPage.jpg"));
    
        BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1200, 800, true, true, true, true));
        Background background = new Background(backgroundImageObject);

        root.setBackground(background);
    }

    public MHTCETApplication(){
        
    }

    public Scene createMHTCETApplicationScene(Stage primaryStage) {

        HBox topBar = createTopBar();
        root.setTop(topBar);

        VBox mainLayout = createMainLayout();
        VBox.setMargin(mainLayout, new Insets(30, 0, 0, 0)); // Add space between top bar and main layout
        root.setCenter(mainLayout);


        return new Scene(root, 1800, 800);
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10, 10, 10, 10));
        topBar.setStyle("-fx-border-radius: 10; -fx-border-width: 1; -fx-border-color: white;");// #008080
        topBar.setAlignment(Pos.CENTER_LEFT);

        Image logoImage = new Image(getClass().getResourceAsStream("/std.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(150);
        logoImageView.setFitHeight(100);
        logoImageView.setPreserveRatio(true);

        Label titleLabel = new Label("College Finder 2024 - Bro Code");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        titleLabel.setTextFill(Color.WHITE);

        VBox logoBox = new VBox(logoImageView, titleLabel);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER);
        searchBar.setPadding(new Insets(10, 10, 10, 10));

        TextField searchField = new TextField();
        searchField.setPromptText("Search Colleges, Courses, Exams, QnA, & Articles");
        searchField.setPrefWidth(800);

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: BLUE; -fx-font-weight: bold;");

        searchBar.getChildren().addAll(searchField, searchButton);

        Button login = new Button("Login");
        login.setOnAction(event -> {
            InitializeApp initializeApp = new InitializeApp();
            Stage inStage = new Stage();
            try {
                initializeApp.start(inStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        topBar.getChildren().addAll(logoBox, searchBar, login);

        return topBar;
    }

    private VBox createMainLayout() {
        Label title = new Label("Select a Exam");
        title.setFont(new Font("Arial", 40));
        title.setPadding(new Insets(10, 5, 10, 230));
        title.setTextFill(Color.BLACK);

        

        Button backButton = new Button("Back");
       // backButton.setOnAction(event -> examSelection.showHomePage());

        

        
        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(title);
        mainLayout.setPadding(new Insets(10, 5, 10, 50));

        return mainLayout;
    }
}