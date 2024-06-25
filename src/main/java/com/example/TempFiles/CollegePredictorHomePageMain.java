package com.example.TempFiles;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class CollegePredictorHomePageMain extends Application {
    private Button backButton;

    @Override
    public void start(Stage primaryStage) {
        // Create the top bar
        HBox topBar = new HBox(20);
        topBar.setPadding(new Insets(10, 10, 10, 10));
        topBar.setStyle("-fx-background-color: #008080;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        // Add logo image
        Image logoImage = new Image(getClass().getResourceAsStream("/std.png")); // Replace with your logo file
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(100);
        logoImageView.setFitHeight(50);
        logoImageView.setPreserveRatio(true);

        // Add title
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
        Button signup = new Button("Sign Up");

        HBox loginsign = new HBox(20, login, signup);
        loginsign.setAlignment(Pos.CENTER_RIGHT);

        // Create the navigation bar
        MenuBar navigationBar = new MenuBar();

        // Create the MBA menu
        Menu mbaMenu = new Menu("MBA");
        mbaMenu.getItems().addAll(
                new MenuItem("Top Ranked Colleges"),
                new MenuItem("Popular Courses"),
                new MenuItem("Exams"),
                new MenuItem("Colleges by Location")
        );

        // Create the Engineering menu
        Menu engineeringMenu = new Menu("Engineering");
        engineeringMenu.getItems().addAll(
                new MenuItem("Top Ranked Colleges"),
                new MenuItem("Popular Courses"),
                new MenuItem("Exams"),
                new MenuItem("Colleges by Location")
        );

        // Create the Medical menu
        Menu medicalMenu = new Menu("Medical");
        medicalMenu.getItems().addAll(
                new MenuItem("Top Ranked Colleges"),
                new MenuItem("Popular Courses"),
                new MenuItem("Exams"),
                new MenuItem("Colleges by Location")
        );

        // Create the More menu
        Menu moreMenu = new Menu("More");
        moreMenu.getItems().addAll(
                new MenuItem("Contact US"),
                new MenuItem("Help"),
                new MenuItem("Report")
        );

        // Create the Study Abroad menu
        Menu studyAbroadMenu = new Menu("Study Abroad");
        studyAbroadMenu.getItems().addAll(
                new MenuItem("Top Ranked Colleges"),
                new MenuItem("Popular Courses"),
                new MenuItem("Exams"),
                new MenuItem("Colleges by Location")
        );

        navigationBar.getMenus().addAll(mbaMenu, engineeringMenu, medicalMenu, moreMenu, studyAbroadMenu);

        VBox navigationBox = new VBox(10, searchBar, navigationBar);
        navigationBox.setAlignment(Pos.CENTER);

        topBar.getChildren().addAll(logoBox, navigationBox, loginsign);

        // Create the steps for the selection process
        StackPane step1 = createStepCircle(1);
        Label step1Label = new Label("Select a Course");
        HBox step1Box = new HBox(10, step1, step1Label);
        step1Box.setAlignment(Pos.CENTER_LEFT);

        StackPane step2 = createStepCircle(2);
        Label step2Label = new Label("Select Exam");
        HBox step2Box = new HBox(10, step2, step2Label);
        step2Box.setAlignment(Pos.CENTER_LEFT);

        StackPane step3 = createStepCircle(3);
        Label step3Label = new Label("Enter Score");
        HBox step3Box = new HBox(10, step3, step3Label);
        step3Box.setAlignment(Pos.CENTER_LEFT);

        VBox steps = new VBox(20, step1Box, step2Box, step3Box);
        steps.setStyle("-fx-background-color:GREY;");
        steps.setPadding(new Insets(20));

        // Create the course selection grid
        GridPane courseGrid = new GridPane();
        courseGrid.setHgap(20);
        courseGrid.setVgap(20);

        // Add courses to the grid
        addCourse("B. Tech.", "/btech.jpg", courseGrid, 0, 0, 38, primaryStage);
        addCourse("MBA", "/Mba.jpeg", courseGrid, 1, 0, 30, primaryStage);
        addCourse("Medical", "/Medical.jpg", courseGrid, 2, 0, 85, primaryStage);
        addCourse("Law", "/Law.jpg", courseGrid, 1, 1, 29, primaryStage);

        VBox news = new VBox(10);

        // Creating and setting the title
        Label newstitleLabel = new Label("Top Ranked Colleges & Related News");
        newstitleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Creating ListViews for different sections
        ListView<String> topRankedColleges = new ListView<>();
        ListView<String> newlyAddedColleges = new ListView<>();
        ListView<String> relatedNews = new ListView<>();

        // Adding sample data
        topRankedColleges.getItems().addAll(
            "1. Dr. D. Y. Patil Institute of Technology, Pune",
            "2. Vishwakarma Institute of Technology (VIT)",
            "3. MIT World Peace University (MIT-WPU)",
            "4. Bharati Vidyapeeth College of Engineering"
        );

        newlyAddedColleges.getItems().addAll(
            "No newly added colleges at this time."
        );

        relatedNews.getItems().addAll(
            "Dr. D. Y. Patil Institute of Technology ranked top in Pune.",
            "VIT introduces new AI curriculum.",
            "MIT-WPU organizes international peace conference.",
            "Bharati Vidyapeeth starts new research center."
        );

        // Adding labels and ListViews to the VBox
        Label topRankedLabel = new Label("Top Ranked Colleges");
        Label newlyAddedLabel = new Label("Newly Added Colleges");
        Label relatedNewsLabel = new Label("Related News");

        news.getChildren().addAll(newstitleLabel, topRankedLabel, topRankedColleges,
                newlyAddedLabel, newlyAddedColleges, relatedNewsLabel, relatedNews);

        // Adding blinking color effect to the labels
        addBlinkingEffect(newstitleLabel);
        addBlinkingEffect(topRankedLabel);
        addBlinkingEffect(newlyAddedLabel);
        addBlinkingEffect(relatedNewsLabel);

        // Create the main layout
        HBox mainLayout = new HBox(50, steps, courseGrid, news);
        mainLayout.setPadding(new Insets(10, 5, 10, 50));

        // Create the footer
        HBox footer = new HBox();
        footer.setStyle("-fx-background-color: #008080;");
        footer.setPadding(new Insets(10));
        footer.setAlignment(Pos.CENTER);

        Label footerLabel = new Label("© 2024 Bro Code. Developed by Bro Code. Contact: prathmeshbondar@gmail.com");
        footerLabel.setTextFill(Color.WHITE);
        footer.getChildren().add(footerLabel);

        // Create the root layout
       BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(mainLayout);
        root.setBottom(footer);

        // Create the scene
        Scene scene = new Scene(root, 1800, 800);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Set the scene and show the stage
        primaryStage.setTitle("Bro Code");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addBlinkingEffect(Label label) {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0), e -> label.setStyle("-fx-text-fill: red;")),
            new KeyFrame(Duration.seconds(0.5), e -> label.setStyle("-fx-text-fill: blue;")),
            new KeyFrame(Duration.seconds(1), e -> label.setStyle("-fx-text-fill: green;"))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // Helper method to create a step circle
    private StackPane createStepCircle(int stepNumber) {
        Circle circle = new Circle(10, Color.TEAL);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1);
        Label stepLabel = new Label(String.valueOf(stepNumber));
        stepLabel.setTextFill(Color.WHITE);
        stepLabel.setAlignment(Pos.CENTER);
        StackPane stackPane = new StackPane(circle, stepLabel);
        return stackPane;
    }

    // Helper method to add a course to the grid
    private void addCourse(String courseName, String imageName, GridPane grid, int column, int row, int examCount, Stage primaryStage) {
        // Create image view
        Image image = new Image(getClass().getResourceAsStream(imageName));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);

        // Create button
        Button courseButton = new Button(courseName);
        courseButton.setPrefWidth(200);
        courseButton.setPrefHeight(50);
        


        // Create label for exam count
        Label examCountLabel = new Label(examCount + " Exams");
        examCountLabel.setAlignment(Pos.CENTER);

        // Add elements to VBox
        VBox courseBox = new VBox(10, imageView, courseButton, examCountLabel);
        courseBox.setAlignment(Pos.CENTER);
        courseBox.setPadding(new Insets(10));

        // Add VBox to grid
        grid.add(courseBox, column, row);

        
    }

   

    public static void main(String[] args) {
        launch(args);
    }
}
