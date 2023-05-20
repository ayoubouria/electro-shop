package com.example.stockmanagement.Controller;

import java.sql.SQLException;
import java.time.LocalDate;

import org.controlsfx.control.Notifications;

import com.example.stockmanagement.Model.Database.UserDB;
import com.example.stockmanagement.Model.Users.Admin;
import com.example.stockmanagement.Model.Users.User;

import atlantafx.base.controls.CustomTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddUserController {

    @FXML
    private Button addUserBtn;

    @FXML
    private CheckBox adminCheck;

    @FXML
    private CustomTextField firstField;

    @FXML
    private CustomTextField lastField;

    @FXML
    private Button returnBtn;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label errorLabel;

    private void clearFields() {
        firstField.setText("");
        lastField.setText("");
    }

    @FXML
    void addUser(ActionEvent event) throws SQLException {
        String firstname = firstField.getText();
        String lastname = lastField.getText();
        boolean isAdmin = adminCheck.isSelected();
        if (firstname.isEmpty() || lastname.isEmpty()) {
            errorLabel.setText("** Please fill all fields **");
            errorLabel.setTextFill(Color.RED);
        } else {
            String username = firstField.getText() + "_" + lastField.getText();
            LocalDate date = LocalDate.now();
            String dateCreated = date.toString();
            String password = "electro_shop";

            User user;
            if (isAdmin) {
                user = new Admin(username, password, firstname, lastname, dateCreated);
            } else {
                user = new User(username, password, firstname, lastname, dateCreated);
            }
            UserDB userDB = new UserDB();
            userDB.insertUser(user);
            Notifications.create()
                    .title("User Added")
                    .text("User Added Successfully")
                    .darkStyle()
                    .position(Pos.TOP_CENTER)
                    .showConfirm();
            clearFields();
        }

    }

    @FXML
    void returnUserPage(MouseEvent event) {
        Stage stage = (Stage) addUserBtn.getScene().getWindow();
        stage.close();
    }

}
