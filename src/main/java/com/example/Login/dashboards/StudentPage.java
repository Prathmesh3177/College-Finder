package com.example.Login.dashboards;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.Login.controller.LoginController;
import com.example.Login.firebaseConfig.DataService;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StudentPage {

    private DataService dataService; 
    private Label dataMsg; 

    public StudentPage(DataService dataService) {
        this.dataService = dataService;
    }

    public VBox createStudentScene(Runnable logoutHandler) {
        dataMsg = new Label();

        Label nameLabel = new Label("Enter name:");
        nameLabel.setStyle("-fx-font-size:12;-fx-font-weight:bold");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        VBox nameBox = new VBox(10, nameLabel, nameField);
        nameBox.setMaxSize(300, 20);

        Label contactLabel = new Label("Enter contact number:");
        contactLabel.setStyle("-fx-font-size:12;-fx-font-weight:bold");
        TextField contactField = new TextField();
        contactField.setPromptText("Contact number");
        VBox contactBox = new VBox(10, contactLabel, contactField);
        contactBox.setMaxSize(300, 20);

        Button addButton = new Button("Add Data");
        addButton.setStyle("-fx-font-size:12;-fx-font-weight:bold");
        HBox buttonBox = new HBox(addButton);
        buttonBox.setAlignment(Pos.CENTER);

        Button logoutButton = new Button("Logout");

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Map<String, Object> data = new HashMap<>();
                data.put("name", nameField.getText());
                data.put("contact", contactField.getText());
                data.put("timestamp", LocalDateTime.now());

                try {
                    dataService.addStudentData(nameField.getText(), data);
                    dataMsg.setText("Added successfully"); 
                    nameField.clear();
                    contactField.clear();
                } catch (ExecutionException | InterruptedException ex) {
                    dataMsg.setText("Something went wrong"); 
                    ex.printStackTrace();
                }
            }
        });

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logoutHandler.run(); 
            }
        });

        Text message = getTheLoginName();

        VBox vButton = new VBox(logoutButton);
        logoutButton.setAlignment(Pos.TOP_LEFT);

        VBox dataBox = new VBox(25, vButton, message, nameBox, contactBox, buttonBox, dataMsg);
        dataBox.setAlignment(Pos.CENTER);
        return dataBox;
    }

    public Text getTheLoginName() {
        Label dataLabel = new Label();
        try {
            String key = LoginController.key;
            DocumentSnapshot dataObject = dataService.getStudentData(key);
            String userName = dataObject.getString("username");
            dataLabel.setText(userName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Text("Welcome " + dataLabel.getText());
    }
}
