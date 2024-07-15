package com.example.HomePage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.BE_ExamSelection.ExamSelection;
import com.example.LAW_ExamSelection.ExamSelectionLAW;
import com.example.Login.firebaseConfig.DataService;
import com.example.Login.initialize.InitializeApp;
import com.example.MBA_ExamSelection.ExamSelectionMBA;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class CollegePredictorHomePage  {
    private Stage primaryStage;
    private Scene scene;
    private BorderPane root;
    private Button backButton;
    private DataService dataService;
    private ObservableList<String> collegeNames = FXCollections.observableArrayList();

    public CollegePredictorHomePage(Stage primaryStage){
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
        initHomePageScene();
    }

    private void initHomePageScene(){
        root = new BorderPane();

        Image backgroundImage = new Image(getClass().getResourceAsStream("/Images/HomePage/wood.png"));
        BackgroundImage backgroundImageObject = new BackgroundImage(backgroundImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1200, 800, true, true, true, true));
        Background background = new Background(backgroundImageObject);
        root.setBackground(background);
    
        HBox topBar = createTopBar();
        root.setTop(topBar);
    
        VBox mainLayout = createMainLayout();
        mainLayout.setAlignment(Pos.CENTER); // Center the main layout
        VBox.setMargin(mainLayout, new Insets(30, 0, 0, 0)); // Add space between top bar and main layout
    
        HBox footer = createFooter();
        root.setBottom(footer);
        root.setCenter(mainLayout);
    
        scene = new Scene(root, 1800, 800);
    
        primaryStage.setTitle("Bro Code");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(300);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
       
        // Create the logo
        Image logoImage = new Image(getClass().getResourceAsStream("/Images/Logo.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(150);
        logoImageView.setFitHeight(100);
        logoImageView.setPreserveRatio(true);
        
        Label lb = new Label("College Finder");
        lb.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        lb.setTextFill(Color.WHITE);

        VBox vb = new VBox(10,logoImageView,lb);
        vb.setPadding(new Insets(0,0, 0, 20));

        ComboBox<String> searchResults = new ComboBox<>();
        searchResults.setPrefWidth(300);

        TextField searchField = new TextField();
        searchField.setPromptText("Search..");
        searchField.setStyle("-fx-background-color: transparent;");
        searchField.setPrefWidth(500);
        searchField.setPrefHeight(20);
        
        // Add search logo
        Image searchIcon = new Image(getClass().getResourceAsStream("/Images/search.png")); 
        ImageView searchImageView = new ImageView(searchIcon);
        searchImageView.setFitWidth(20);
        searchImageView.setFitHeight(20);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String normalizedQuery = newValue.toLowerCase(); 
        
            if (!normalizedQuery.isEmpty() && normalizedQuery.length() >= 3) {
                try {
                    String queryPrefix = normalizedQuery.substring(0, 3);
                    List<Map<String, Object>> colleges = dataService.getCollegesByInitialLetters(queryPrefix);
                    System.out.println("Colleges retrieved: " + colleges.size());
        
                    collegeNames.clear(); 
                    for (Map<String, Object> college : colleges) {
                        String collegeName = (String) college.get("name");
                        collegeNames.add(collegeName);
                    }
                    searchResults.setItems(collegeNames);
                    searchResults.show(); 
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                
                }
            } else {
                collegeNames.clear(); 
                searchResults.getItems().clear();
                searchResults.hide(); 
            }
        });
        
        searchField.setOnMouseClicked(event -> {
            if (searchField.getText().isEmpty()) {
                collegeNames.clear();
                searchResults.getItems().clear();
                searchResults.hide();
            }
        });
        
        searchResults.setPrefWidth(500);
        searchResults.setStyle("-fx-background-color: transparent; ");
        VBox serBox = new VBox(searchField,searchResults);
        HBox hbox = new HBox(5); // 5 is the spacing between the icon and the text field
        hbox.getChildren().addAll(searchImageView, serBox);
        hbox.setPadding(new Insets(10,0,0,0));

        searchField.setPadding(new Insets(0, 5, 0, 0)); 
        searchField.setStyle("-fx-background-color: transparent; ");

        searchField.setOnMouseEntered(event -> {
            searchField.setPrefWidth(600);
        });

        searchField.setOnMouseExited(event -> {
            searchField.setPrefWidth(500);
        });

        HBox imgser = new HBox(70,vb,hbox);
    
        searchField.setStyle("-fx-background-radius: 10; -fx-border-width: 1; -fx-border-radius: 10;");

        Button homeButton = new Button("Home");
        homeButton.setStyle(
                    "-fx-background-color: transparent;");
        homeButton.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        
        Button aboutButton = new Button("About");
        aboutButton.setStyle(
                    "-fx-background-color: transparent;");
        aboutButton.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        ContextMenu aboutcontextMenu = new ContextMenu();
        aboutcontextMenu.setStyle("-fx-background-color: transparent;");

        MenuItem Contact = new MenuItem("Contact Us");
        Contact.setStyle("-fx-background-color: transparent;");
        MenuItem Info = new MenuItem("Info");
        Info.setStyle("-fx-background-color: transparent;");
        MenuItem Help = new MenuItem("Help");
        Help.setStyle("-fx-background-color: transparent;");

        aboutcontextMenu.getItems().addAll(Contact,Info,Help);

        aboutButton.setOnAction(event -> {
            aboutcontextMenu.show(aboutButton, Side.BOTTOM, 0, 0);
        });
        Button coursesButton = new Button("Courses");
        coursesButton.setStyle(
                    "-fx-background-color: transparent;");
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
        
        HBox Buttonbox = new HBox(10,homeButton, aboutButton, coursesButton,loginButton);
        topBar.getChildren().addAll(imgser,Buttonbox);

        // Create the main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().add(topBar);

        return topBar;

    }

    private VBox createMainLayout() {
        Label lb = new Label("Find Your Dream College...");
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/AcademyEngravedLetPlain.ttf"), 55);
        lb.setFont(font);
        lb.setStyle("-fx-font-weight: bold;");

        GridPane courseGrid = createCourseGrid();

        Image gradCap = new Image(getClass().getResourceAsStream("/Images/HomePage/pngwing.com.png"));
        ImageView gradCapImageView = new ImageView(gradCap);
        
        // Set the initial size of the image
        gradCapImageView.setFitWidth(600);
        gradCapImageView.setFitHeight(600);
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(10);
        dropShadow.setSpread(0.2);
        
        gradCapImageView.setEffect(dropShadow);
        
        VBox image = new VBox(gradCapImageView);
        image.setPadding(new Insets(0,100,0,0));

        VBox vb = new VBox(20,lb,courseGrid);
        HBox hb = new HBox(60,vb,image);
       
        HBox mainLayout = new HBox(50,hb);//steps, news
        mainLayout.setPadding(new Insets(10, 5, 10, 50));

        return new VBox(mainLayout);
    }

    private GridPane createCourseGrid() {
        GridPane courseGrid = new GridPane();
        courseGrid.setHgap(20);
        courseGrid.setVgap(20);

        addCourse("B.Tech.", "/Images/HomePage/btech1.png", courseGrid, 0, 0, 3, this::handleBTechClick);
        addCourse("MBA", "/Images/HomePage/Mba.png", courseGrid, 1, 0, 3, this::handleMBAClick);
        addCourse("Medical", "/Images/HomePage/Medical.png", courseGrid, 2, 0, 2, this::handleMedicalClick);
        addCourse("Law", "/Images/HomePage/Law.png", courseGrid, 1, 1, 2, this::handleLawClick);

        return courseGrid;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        //footer.setStyle("-fx-background-color: #008080;");
        footer.setPadding(new Insets(10));
        footer.setAlignment(Pos.CENTER);

        Label footerLabel = new Label("© 2024 Bro Code. Developed by Bro Code. Contact: prathmeshbondar@gmail.com");
        footerLabel.setTextFill(Color.WHITE);
        footer.getChildren().add(footerLabel);

        return footer;
    }

    private void addCourse(String courseName, String imageName, GridPane grid, int column, int row, int examCount, EventHandler<ActionEvent> action) {
    Image image = new Image(getClass().getResourceAsStream(imageName));
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(200);
    imageView.setFitHeight(150);

    // Add a drop shadow effect to the image view
    DropShadow dropShadow = new DropShadow();
    dropShadow.setOffsetX(5);
    dropShadow.setOffsetY(5);
    imageView.setEffect(dropShadow);
    imageView.setStyle("-fx-background-color: #964B00; -fx-background-radius: 10;");

    // Add a floating icon effect
    imageView.setTranslateY(10);
    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), imageView);
    translateTransition.setFromY(10);
    translateTransition.setToY(0);
    translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
    translateTransition.setAutoReverse(true);
    translateTransition.play();
    
    Button courseButton = new Button(courseName);
    courseButton.setPrefWidth(200);
    courseButton.setPrefHeight(50);

    // Set the button's background to transparent
    courseButton.setStyle("-fx-background-color: transparent;");

    // Set the button's border to a rounded rectangle
    courseButton.setStyle("-fx-border-radius: 10; -fx-border-width: 1; -fx-border-color: white;");

    // Create a hover effect to change the button's background to white
    courseButton.hoverProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue) {
            courseButton.setStyle(
                    "-fx-background-color: white ; -fx-border-radius: 10; -fx-border-width: 1; -fx-border-color: white;");
        } else {
            courseButton.setStyle(
                    "-fx-background-color: transparent; -fx-border-radius: 10; -fx-border-width: 1; -fx-border-color: white;");
        }
    });

    Label examCountLabel = new Label(examCount + " Exams");
    examCountLabel.setAlignment(Pos.CENTER);

    VBox courseBox = new VBox(10, imageView, courseButton, examCountLabel);
    courseBox.setAlignment(Pos.CENTER);
    courseBox.setPadding(new Insets(10));

    grid.add(courseBox, column, row);

    courseButton.setOnAction(action);
}

    private void handleBTechClick(ActionEvent event) {
        updateMainContentForBTech();
    }

    private void handleMBAClick(ActionEvent event) {
        updateMainContentForMBA();
    }

    private void handleMedicalClick(ActionEvent event) {
        updateMainContentForMedical();
    }

    private void handleLawClick(ActionEvent event) {
        updateMainContentForLaw();
    }

    private void updateMainContentForBTech() {
        ExamSelection examSelection = new ExamSelection(this);
        Scene ExamSelectionScene = examSelection.createExamSelectionScene(primaryStage);
        primaryStage.setScene(ExamSelectionScene);
        primaryStage.show();
    }

    private void updateMainContentForMBA() {
        //  logic for MBA course selection
        ExamSelectionMBA examSelectionmba = new ExamSelectionMBA(this);
        Scene ExamSelectionMBAScene = examSelectionmba.createExamSelectionMBAScene(primaryStage);
        primaryStage.setScene(ExamSelectionMBAScene);
        primaryStage.show();
    }

    private void updateMainContentForMedical() {
        // for Medical course selection
    }

    private void updateMainContentForLaw() {
        // for Law course selection
        ExamSelectionLAW examSelectionlaw = new ExamSelectionLAW(this);
        Scene ExamSelectionLAWScene = examSelectionlaw.createExamSelectionLAWScene(primaryStage);
        primaryStage.setScene(ExamSelectionLAWScene);
        primaryStage.show();
    }

    private void initializeBackButton() {
        backButton = new Button("Home Page");
        backButton.setOnAction(event -> showHomePage());
    }


    public Scene getHomePageScene() {
        return scene;
    }
    public void showHomePage() {
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
    public void createMHTCETApplicationScene() {
        
        primaryStage.setScene(scene);
        primaryStage.show();  
    }

}
