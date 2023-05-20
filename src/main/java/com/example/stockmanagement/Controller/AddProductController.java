package com.example.stockmanagement.Controller;

import java.io.IOException;
import java.sql.SQLException;

import org.controlsfx.control.Notifications;

import com.example.stockmanagement.Main;
import com.example.stockmanagement.Model.Classes.Product;
import com.example.stockmanagement.Model.Database.ProductDB;

import atlantafx.base.controls.CustomTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddProductController {

    @FXML
    private Button addProductBtn;

    @FXML
    private CustomTextField categoryField;

    @FXML
    private CustomTextField codeField;

    @FXML
    private CustomTextField nameField;

    @FXML
    private CustomTextField positionField;

    @FXML
    private CustomTextField priceField;

    @FXML
    private CustomTextField quantityField;

    @FXML
    private Button returnBtn;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label errorLabel;

    @FXML
    void addProduct(ActionEvent event) throws SQLException {
        String name = nameField.getText();
        String code = codeField.getText();
        String category = categoryField.getText();
        String position = positionField.getText();
        int price = Integer.parseInt(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        if (name.isEmpty() || code.isEmpty() || category.isEmpty() || position.isEmpty()
                || priceField.getText().isEmpty() || quantityField.getText().isEmpty()) {
            errorLabel.setText("Please fill all the fields");
            errorLabel.setTextFill(Color.RED);
        } else {
            Product product = new Product(name, code, category, price, quantity, position);
            ProductDB productDB = new ProductDB();
            productDB.insertProduct(product);
            Notifications.create()
                    .title("Product Added")
                    .text("Product Added Successfully")
                    .darkStyle()
                    .position(Pos.TOP_CENTER)
                    .showConfirm();
            clearFields();
        }

    }

    @FXML
    void showProductPage(MouseEvent event) throws IOException {
        Stage stage = (Stage) returnBtn.getScene().getWindow();
        stage.close();

    }

    private void clearFields() {
        nameField.clear();
        codeField.clear();
        categoryField.clear();
        positionField.clear();
        priceField.clear();
        quantityField.clear();
    }

}
