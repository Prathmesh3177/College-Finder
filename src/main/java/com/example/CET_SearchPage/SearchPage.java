package com.example.CET_SearchPage;

import java.net.URI;
import java.util.Map;
import java.awt.Desktop;

import com.example.BE_ExamSelection.ExamSelection;
import com.example.HomePage.CollegePredictorHomePage;
import com.example.Login.firebaseConfig.DataService;
import com.example.Login.initialize.InitializeApp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class SearchPage {
    private Stage primaryStage;
    private BorderPane root;
    private Scene scene;
    private DataService dataService;
    private String selectedExam;
    private ExamSelection examSelection;
    private CollegePredictorHomePage collegePredictorHomePage;
    private Popup currentPopup;

    public SearchPage(String selectedExam, DataService dataService, CollegePredictorHomePage collegePredictorHomePage) {
        this.dataService = dataService;
        this.selectedExam = selectedExam;
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

    public Scene createSearchScene(Stage primaryStage) {
        this.primaryStage = primaryStage;

        HBox topBar = createTopBar();
        root.setTop(topBar);

        VBox mainLayout = createMainLayout();
        mainLayout.setPadding(new Insets(30, 250, 30, 0)); // top, right, bottom, left
        // mainLayout.setAlignment(Pos.TOP_LEFT); // Add space between top bar and main
        // layout
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
        searchField.setPrefWidth(500);
        searchField.setPrefHeight(20);
        searchField.setStyle("-fx-background-color: transparent;");

        // Add search logo
        Image searchIcon = new Image(getClass().getResourceAsStream("/Images/search.png")); // replace with your icon image
        ImageView searchImageView = new ImageView(searchIcon);
        searchImageView.setFitWidth(20);
        searchImageView.setFitHeight(20);

        // Create an HBox to layout the icon and the text field
        HBox hbox = new HBox(5);
        hbox.getChildren().addAll(searchImageView, searchField);
        hbox.setPadding(new Insets(10, 0, 0, 0));

        // Add some padding to the left of the text field
        searchField.setPadding(new Insets(0, 5, 0, 0)); // adjust the values as needed

        // Increase size on hover
        searchField.setOnMouseEntered(event -> searchField.setPrefWidth(600));
        searchField.setOnMouseExited(event -> searchField.setPrefWidth(500));

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
        loginButton.setStyle(
                "-fx-background-color: white; -fx-border-radius: 10; -fx-border-width: 1; -fx-border-color: white;");
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
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Search College");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label preferredCourseLabel = new Label("Select Preferred Course");
        preferredCourseLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold");
        ComboBox<String> preferredCourseComboBox = new ComboBox<>();
        preferredCourseComboBox.getItems().add("BE/BTech");

        Label examRankLabel = new Label("Enter Exam Rank/Score");
        examRankLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold");
        TextField searchField = new TextField();
        searchField.setPromptText("Enter Score/Marks");

        Label genderLabel = new Label("Gender");
        genderLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold");
        RadioButton maleRadioButton = new RadioButton("Male");
        RadioButton femaleRadioButton = new RadioButton("Female");
        RadioButton otherRadioButton = new RadioButton("Other");
        ToggleGroup genderGroup = new ToggleGroup();
        maleRadioButton.setToggleGroup(genderGroup);
        femaleRadioButton.setToggleGroup(genderGroup);
        otherRadioButton.setToggleGroup(genderGroup);

        Label categoryLabel = new Label("Category");
        categoryLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.setPromptText("Select Category");
        categoryComboBox.getItems().addAll("OPEN", "OBC", "NT-D", "NT-C", "NT-B", "NT-A", "SC", "ST");

        Label domicileUniversityLabel = new Label("University");
        domicileUniversityLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold");
        ComboBox<String> universityComboBox = new ComboBox<>();
        universityComboBox.setPromptText("Select University");
        universityComboBox.getItems().addAll("HOME UNIVERSITY", "OTHER THAN HOME UNIVERSITY");


        Button searchButton = new Button("Search");
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            ExamSelection examSelection = new ExamSelection(collegePredictorHomePage);
            Scene examSelectionScene = examSelection.createExamSelectionScene(primaryStage);
            primaryStage.setScene(examSelectionScene);
        });

        GridPane inputGrid = new GridPane();
        inputGrid.setAlignment(Pos.CENTER);
        inputGrid.setHgap(10);
        inputGrid.setVgap(30);

        inputGrid.add(preferredCourseLabel, 0, 0);
        inputGrid.add(preferredCourseComboBox, 1, 0);
        inputGrid.add(examRankLabel, 0, 1);
        inputGrid.add(searchField, 1, 1);
        inputGrid.add(genderLabel, 0, 2);

        HBox genderBox = new HBox(10);
        genderBox.getChildren().addAll(maleRadioButton, femaleRadioButton, otherRadioButton);
        inputGrid.add(genderBox, 1, 2);

        inputGrid.add(categoryLabel, 0, 3);
        inputGrid.add(categoryComboBox, 1, 3);
        inputGrid.add(domicileUniversityLabel, 0, 4);
        inputGrid.add(universityComboBox, 1, 4);
        

        ListView<String> searchResults = new ListView<>();
        searchResults.setMaxWidth(400);
        searchResults.setMaxHeight(300);
        searchResults.setStyle("-fx-background-color: transperant; -fx-background-radius: 10;");

        searchResults.setCellFactory(listView -> new ListCell<String>() {
            private HBox hbox;
            private TextField textField;
            private Button infoButton;

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle(
                            "-fx-background-color: WHITE; -fx-padding: 1; -fx-border-width: 1; -fx-border-color: BLACK;");
                    setGraphic(null);
                } else {
                    if (hbox == null) {
                        hbox = new HBox(5);
                        textField = new TextField();
                        textField.setPrefWidth(350);
                        textField.setStyle(
                                "-fx-background-color: transparent; -fx-padding: 1; -fx-border-width: 1; -fx-border-color: BLACK;");
                        infoButton = new Button("i"); // create a small button with an "i" icon
                        infoButton.setStyle("-fx-padding: 2; -fx-font-size: 20; -fx-background-color: WHITE;"); // style
                                                                                                                // the
                                                                                                                // button
                        infoButton.setOnAction(e -> {
                            try {
                                // Get college data from Firestore
                                String collegeName = item.split(":")[0].trim(); // Assuming item format is "College
                                                                                // Name: XYZ"
                                Map<String, Object> collegeData = dataService.getCollegeData(collegeName);
                               // displayCollegeInfo(collegeData);
                                displayCollegeInfo(primaryStage, collegeData);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                showAlert("Error fetching college information.");
                            }
                        }); // set the action for the button
                        hbox.getChildren().addAll(textField, infoButton);
                    }
                    textField.setText(item);
                    setGraphic(hbox);
                    setText(null);
                }
            }
        });

        searchButton.setOnAction(event -> {
            String cutoffMarks = searchField.getText().trim();
            String gender = genderGroup.getSelectedToggle() != null
                    ? ((RadioButton) genderGroup.getSelectedToggle()).getText()
                    : null;
            String category = categoryComboBox.getValue();
            String university = universityComboBox.getValue();
            
            String preferredCourse = preferredCourseComboBox.getValue();

            if (cutoffMarks.isEmpty()) {
                showAlert("Please enter cutoff marks.");
                return;
            }

            if (gender == null) {
                showAlert("Please select a gender.");
                return;
            }

            if (category == null) {
                showAlert("Please select a category.");
                return;
            }

            if (university == null) {
                showAlert("Please select a university.");
                return;
            }

            

            if (preferredCourse == null) {
                showAlert("Please select a preferred course.");
                return;
            }

            if (dataService == null) {
                showAlert("Data service is not initialized.");
                return;
            }

            try {
                double cutoffValue = Double.parseDouble(cutoffMarks);
                Map<String, Object> colleges = dataService.getCollegeDataByCutoffAndCategory(cutoffValue, category);

                searchResults.getItems().clear();
                if (colleges.isEmpty()) {
                    showAlert("No colleges found with the specified cutoff marks and category.");
                } else {
                    for (Map.Entry<String, Object> entry : colleges.entrySet()) {
                        searchResults.getItems().add(entry.getKey() + ": " + entry.getValue().toString());
                    }
                }
            } catch (NumberFormatException e) {
                showAlert("Please enter a valid number for cutoff marks.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        searchResults.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                try {
                    // Get college data from Firestore
                    Map<String, Object> collegeData = (Map<String, Object>) dataService.getCollegeData(newVal);
                   // displayCollegeInfo(collegeData);
                    displayCollegeInfo(primaryStage, collegeData);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        HBox backSerach = new HBox(20, backButton, searchButton);
        backSerach.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(titleLabel, inputGrid, backSerach, searchResults);

        return layout;
    }

    private void displayCollegeInfo(Stage ownerStage, Map<String, Object> collegeData) {
        System.out.println("Displaying college info...");
        if (collegeData == null || collegeData.isEmpty()) {
            System.out.println("College data is empty.");
            return;
        }
    
        // Hide the current popup if it exists
        if (currentPopup != null && currentPopup.isShowing()) {
            currentPopup.hide();
        }
    
        currentPopup = new Popup();
    
        Label nameLabel = new Label("College Name: " + collegeData.get("name"));
        nameLabel.setStyle(" -fx-font-weight: bold;");
    
        Label locationLabel = new Label("Location: " + collegeData.get("location"));
        locationLabel.setStyle(" -fx-font-weight: bold;");
    
        Label branchLabel = new Label("Branch: " + collegeData.get("branch"));
        branchLabel.setStyle(" -fx-font-weight: bold;");
    
        Label examLabel = new Label("Exam: " + collegeData.get("exam"));
        examLabel.setStyle(" -fx-font-weight: bold;");
    
        // Removed websiteLabel, using websiteLink instead
        Hyperlink websiteLink = new Hyperlink("College Website Link: " + collegeData.get("website"));
        websiteLink.setStyle("-fx-font-weight: bold;");
        websiteLink.setOnAction(event -> {
            try {
                URI uri = new URI(collegeData.get("website").toString());
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        Label intakeLabel = new Label("Intake: " + collegeData.get("intake"));
        intakeLabel.setStyle(" -fx-font-weight: bold;");
    
        Map<String, String> cutoffMarks = (Map<String, String>) collegeData.get("cutoff_marks");
        VBox cutoffMarksBox = new VBox(10);
        cutoffMarksBox.setAlignment(Pos.CENTER_LEFT);
        cutoffMarksBox.setPadding(new Insets(0, 0, 0, 20));
        cutoffMarksBox.setStyle(
                "-fx-padding: 10; -fx-background-color: #f0f0f0; -fx-border-color: #dcdcdc; -fx-border-width: 1;");
        Label cutoffMarksLabel = new Label("Cutoff Marks");
        cutoffMarksLabel.setStyle("-fx-font-weight: bold;");
        cutoffMarksBox.getChildren().add(cutoffMarksLabel);
    
        for (String category : cutoffMarks.keySet()) {
            Label cutoffMarkLabel = new Label(category + ": " + cutoffMarks.get(category));
            cutoffMarksBox.getChildren().add(cutoffMarkLabel);
            cutoffMarkLabel.setStyle("-fx-font-weight: bold;");
        }
    
        VBox infoLayout = new VBox(15);
        infoLayout.setLayoutX(500);
        infoLayout.setLayoutY(200);
        infoLayout.setStyle("-fx-background-color: #ffffff;");
        infoLayout.setAlignment(Pos.CENTER_LEFT);
        infoLayout.setPadding(new Insets(20));
        infoLayout.getChildren().addAll(
                nameLabel,
                locationLabel,
                branchLabel,
                examLabel,
                websiteLink, // Add websiteLink instead of websiteLabel
                intakeLabel,
                cutoffMarksBox);
    
        currentPopup.getContent().add(infoLayout);
    
        // Show popup relative to the owner stage
        currentPopup.show(ownerStage);
    
        ownerStage.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (currentPopup.isShowing() && !infoLayout.contains(event.getSceneX(), event.getSceneY())) {
                currentPopup.hide();
            }
        });
    
        // Close popup on click
        infoLayout.setOnMouseClicked(event -> currentPopup.hide());
    }
    


    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
