package com.example.Login.dashboards;

import com.example.Login.controller.LoginController;
import com.example.Login.firebaseConfig.DataService;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class CollegePage {

    private LoginController loginController;
    private DataService dataService;

    public CollegePage(LoginController loginController, DataService dataService) {
        this.loginController = loginController;
        this.dataService = dataService;
    }

    public Parent createCollegeDashboard(Runnable logoutHandler) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("College Dashboard");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox nameBox = new VBox(5);
        Label nameLabel = new Label("College Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter College Name");
        nameBox.getChildren().addAll(nameLabel, nameField);

        VBox locationBox = new VBox(5);
        Label locationLabel = new Label("Location:");
        TextField locationField = new TextField();
        locationField.setPromptText("Enter College Location");
        locationBox.getChildren().addAll(locationLabel, locationField);

        VBox branchBox = new VBox(5);
        Label branchLabel = new Label("Branch:");
        ComboBox<String> branchComboBox = new ComboBox<>();
        branchComboBox.getItems().addAll(
                "Mechatronics Engineering",
                "Computer Science Engineering",
                "Civil Engineering",
                "Mechanical Engineering",
                "ECE (Electronics and Communications Engineering)",
                "Electrical Engineering",
                "Aerospace Engineering",
                "Chemical Engineering",
                "Marine Engineering",
                "Automobile Engineering",
                "Software Engineering"
        );
        branchBox.getChildren().addAll(branchLabel, branchComboBox);

        VBox examBox = new VBox(5);
        Label examLabel = new Label("Exam:");
        ComboBox<String> examComboBox = new ComboBox<>();
        examComboBox.getItems().addAll("MHT-CET", "JEE Main", "JEE Advanced");
        examBox.getChildren().addAll(examLabel, examComboBox);

        VBox cutoffMarksVBox = new VBox(10);
        Label cutoffMarksLabel = new Label("Cutoff Marks by Category and Caste:");
        cutoffMarksVBox.getChildren().add(cutoffMarksLabel);

        String[] categories = {"SEBC", "OPEN", "OBC", "NT-D", "NT-C", "NT-B", "NT-A", "SC", "ST"};
        Map<String, Map<String, TextField>> cutoffFields = new HashMap<>();

        for (String category : categories) {
            HBox categoryBox = new HBox(10);
            categoryBox.setAlignment(Pos.CENTER_LEFT);
            Label categoryLabel = new Label(category + ":");
            TextField cutoffField = new TextField();
            cutoffField.setPromptText("Enter Cutoff Marks for " + category);
            categoryBox.getChildren().addAll(categoryLabel, cutoffField);
            cutoffMarksVBox.getChildren().add(categoryBox);

            cutoffFields.putIfAbsent(category, new HashMap<>());
            cutoffFields.get(category).put("MHT-CET", new TextField());
            cutoffFields.get(category).put("JEE Main", new TextField());
            cutoffFields.get(category).put("JEE Advanced", new TextField());
        }

        VBox intakeBox = new VBox(5);
        Label intakeLabel = new Label("Intake:");
        TextField intakeField = new TextField();
        intakeField.setPromptText("Enter Intake for Selected Branch");
        intakeBox.getChildren().addAll(intakeLabel, intakeField);

        VBox websiteBox = new VBox(5);
        Label websiteLabel = new Label("College Website Link:");
        TextField websiteField = new TextField();
        websiteField.setPromptText("Enter Website Link");
        websiteBox.getChildren().addAll(websiteLabel, websiteField);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String name = nameField.getText().trim();
            String location = locationField.getText().trim();
            String branch = branchComboBox.getValue();
            String exam = examComboBox.getValue();
            String websiteLink = websiteField.getText().trim();
            String intake = intakeField.getText().trim();

            if (!name.isEmpty() && !location.isEmpty() && branch != null && exam != null && !websiteLink.isEmpty() && !intake.isEmpty()) {
                // Create data map to store in Firestore
                Map<String, Object> collegeData = new HashMap<>();
                collegeData.put("name", name);
                collegeData.put("location", location);
                collegeData.put("branch", branch);
                collegeData.put("exam", exam);
                collegeData.put("website", websiteLink);
                collegeData.put("intake", intake);

                // Add cutoff marks to the data map
                Map<String, String> cutoffMarks = new HashMap<>();
                for (String category : categories) {
                    String cutoff = cutoffFields.get(category).get(exam).getText().trim();
                    if (!cutoff.isEmpty()) {
                        cutoffMarks.put(category, cutoff);
                    }
                }
                collegeData.put("cutoff_marks", cutoffMarks);

                try {
                    // Add college data to Firestore using DataService
                    dataService.addCollegeData(name, collegeData);
                    System.out.println("College data added successfully.");

                    // Clear fields after successful submission
                    nameField.clear();
                    locationField.clear();
                    branchComboBox.setValue(null);
                    examComboBox.setValue(null);
                    websiteField.clear();
                    intakeField.clear();
                    cutoffFields.values().forEach(map -> map.values().forEach(TextField::clear));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Please fill in all fields.");
            }
        });

        Button dashboardButton = new Button("Dashboard");
        dashboardButton.setOnAction(event -> {
            VBox dashboardLayout = new VBox(20);
            dashboardLayout.setPadding(new Insets(20));
            dashboardLayout.setAlignment(Pos.CENTER);

            Label dashboardTitleLabel = new Label("College Data Dashboard");
            dashboardTitleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
            dashboardLayout.getChildren().add(dashboardTitleLabel);

            TableView<Map.Entry<String, Object>> tableView = new TableView<>();
            TableColumn<Map.Entry<String, Object>, String> keyColumn = new TableColumn<>("Key");
            keyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
            TableColumn<Map.Entry<String, Object>, String> valueColumn = new TableColumn<>("Value");
            valueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));
            tableView.getColumns().addAll(keyColumn, valueColumn);

            try {
                Map<String, Object> collegeData = dataService.getCollegeData(nameField.getText().trim());
                tableView.getItems().addAll(collegeData.entrySet());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Button backButton = new Button("Back");
            backButton.setOnAction(e -> {
                // Clear the dashboard and show the form again
                layout.getChildren().clear();
                layout.getChildren().addAll(
                        titleLabel,
                        nameBox,
                        locationBox,
                        branchBox,
                        examBox,
                        cutoffMarksVBox,
                        intakeBox,
                        websiteBox,
                        submitButton,
                        dashboardButton
                );
            });

            dashboardLayout.getChildren().addAll(tableView, backButton);
            layout.getChildren().clear();
            layout.getChildren().add(dashboardLayout);
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> logoutHandler.run());

        layout.getChildren().addAll(
                titleLabel,
                nameBox,
                locationBox,
                branchBox,
                examBox,
                cutoffMarksVBox,
                intakeBox,
                websiteBox,
                submitButton,
                dashboardButton,
                logoutButton
        );

        // Add listener to update cutoff fields based on selected exam
        examComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            cutoffMarksVBox.getChildren().clear();
            cutoffMarksVBox.getChildren().add(cutoffMarksLabel);
            if (newVal != null) {
                for (String category : categories) {
                    HBox categoryBox = new HBox(10);
                    categoryBox.setAlignment(Pos.CENTER_LEFT);
                    Label categoryLabel = new Label(category + ":");
                    TextField cutoffField = cutoffFields.get(category).get(newVal);
                    categoryBox.getChildren().addAll(categoryLabel, cutoffField);
                    cutoffMarksVBox.getChildren().add(categoryBox);
                }
            }
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }
}
