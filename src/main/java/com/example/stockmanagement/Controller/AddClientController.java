package com.example.stockmanagement.Controller;

import java.sql.SQLException;

import org.controlsfx.control.Notifications;

import com.example.stockmanagement.Model.Classes.Client;
import com.example.stockmanagement.Model.Database.ClientsDB;

import atlantafx.base.controls.CustomTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AddClientController {

    @FXML
    private Button addClientBtn;

    @FXML
    private TextArea adressField;

    @FXML
    private CustomTextField clientNameField;

    @FXML
    private CustomTextField emailField;

    @FXML
    private Label errorLabel;

    @FXML
    private CustomTextField phoneField;

    @FXML
    private Button returnBtn;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    void addClient(ActionEvent event) throws SQLException {
        String clientName = clientNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = adressField.getText();

        if (clientName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            errorLabel.setText("** Please fill all fields **");
            errorLabel.setTextFill(Color.RED);
        } else {
            Client client = new Client(clientName, email, phone, address);
            ClientsDB clientDB = new ClientsDB();
            clientDB.insertClient(client);
            Notifications.create()
                    .title("Client added")
                    .text("Client Added Successfully")
                    .darkStyle()
                    .position(Pos.TOP_CENTER)
                    .showConfirm();
            clearFields();
        }
    }

    @FXML
    void returnClientPage(MouseEvent event) {
        Stage stage = (Stage) addClientBtn.getScene().getWindow();
        stage.close();
    }

    private void clearFields() {
        clientNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        adressField.setText("");
    }

}
