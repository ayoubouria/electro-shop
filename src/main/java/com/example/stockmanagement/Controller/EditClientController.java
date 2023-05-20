package com.example.stockmanagement.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.example.stockmanagement.Cache.ClientCache;
import com.example.stockmanagement.Model.Classes.Client;
import com.example.stockmanagement.Model.Database.ClientsDB;

import atlantafx.base.controls.CustomTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EditClientController implements Initializable {

    @FXML
    private TextArea adressField;

    @FXML
    private CustomTextField clientNameField;

    @FXML
    private Button editClientBtn;

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
    void editClient(ActionEvent event) throws SQLException {
        String clientName = clientNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = adressField.getText();
        ClientCache clientCache = ClientCache.getInstance();
        Client client = clientCache.getClient();

        if (clientName.equals(client.getName()) && email.equals(client.getEmail()) && phone.equals(client.getPhone())
                && address.equals(client.getAddress())) {
            errorLabel.setText("** Please change at least one field **");
            errorLabel.setTextFill(Color.RED);
        } else {
            client.setName(clientName);
            client.setEmail(email);
            client.setPhone(phone);
            client.setAddress(address);
            ClientsDB clientDB = new ClientsDB();
            clientDB.updateClient(client);
            clientCache.setClient(client);
            Notifications.create()
                    .title("Client updated")
                    .text("Client updated Successfully")
                    .darkStyle()
                    .position(Pos.TOP_CENTER)
                    .showConfirm();
        }

    }

    @FXML
    void returnClientPage(MouseEvent event) {
        Stage stage = (Stage) rootAnchorPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientCache clientCache = ClientCache.getInstance();
        Client client = clientCache.getClient();
        clientNameField.setText(client.getName());
        emailField.setText(client.getEmail());
        phoneField.setText(client.getPhone());
        adressField.setText(client.getAddress());

    }

}
