package com.example.stockmanagement.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.example.stockmanagement.Cache.UserCache;
import com.example.stockmanagement.Model.Database.UserDB;
import com.example.stockmanagement.Model.Users.User;

import atlantafx.base.controls.CustomTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EditUserController implements Initializable {
    @FXML
    private Button editUserBtn;

    @FXML
    private CustomTextField firstName;

    @FXML
    private CustomTextField lastField;

    @FXML
    private CustomTextField passwordField;

    @FXML
    private Button returnBtn;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private CustomTextField usernameFiels;
    @FXML
    private Label errorLabel;

    @FXML
    void editUser(ActionEvent event) throws SQLException {
        String username = usernameFiels.getText();
        String password = passwordField.getText();
        String firstname = firstName.getText();
        String lastname = lastField.getText();
        UserCache userCache = UserCache.getInstance();
        User user = userCache.getUser();
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())
                && firstname.equals(user.getFirstName()) && lastname.equals(user.getLastName())) {
            errorLabel.setText("** Please change at least one field **");
            errorLabel.setTextFill(Color.RED);
        } else {
            UserDB userDB = new UserDB();
            userDB.updateUser(user);
            Notifications.create()
                    .title("Client updated")
                    .text("Client updated Successfully")
                    .darkStyle()
                    .position(Pos.TOP_CENTER)
                    .showConfirm();
        }
    }

    @FXML
    void returnUserPage(MouseEvent event) {
        Stage stage = (Stage) editUserBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserCache userCache = UserCache.getInstance();
        User user = userCache.getUser();
        usernameFiels.setText(user.getUsername());
        firstName.setText(user.getFirstName());
        lastField.setText(user.getLastName());
        passwordField.setText("");
    }

}
