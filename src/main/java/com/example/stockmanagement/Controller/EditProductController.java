package com.example.stockmanagement.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.example.stockmanagement.Cache.ProductCache;
import com.example.stockmanagement.Model.Classes.Product;
import com.example.stockmanagement.Model.Database.ProductDB;

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

public class EditProductController implements Initializable {

    @FXML
    private CustomTextField categoryField;

    @FXML
    private CustomTextField codeField;

    @FXML
    private Button editProductBtn;

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
    void editProduct(ActionEvent event) throws SQLException {
        String name = nameField.getText();
        String code = codeField.getText();
        String category = categoryField.getText();
        String position = positionField.getText();
        int price = Integer.parseInt(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        ProductCache productCache = ProductCache.getInstance();
        Product product = productCache.getProduct();
        if (name.equals(product.getName()) && code.equals(product.getCode()) && category.equals(product.getCategory())
                && position.equals(product.getPosition()) && price == product.getPrice()
                && quantity == product.getQuantity()) {
            errorLabel.setText("No changes made");
            errorLabel.setTextFill(Color.RED);
        } else {
            product.setName(name);
            product.setCode(code);
            product.setCategory(category);
            product.setPosition(position);
            product.setPrice(price);
            product.setQuantity(quantity);
            productCache.setProduct(product);
            ProductDB productDB = new ProductDB();
            productDB.updateProduct(product);
            Notifications.create()
                    .title("Product updated")
                    .text("Product updated Successfully")
                    .darkStyle()
                    .position(Pos.TOP_CENTER)
                    .showConfirm();
        }
    }

    @FXML
    void returnProductPage(MouseEvent event) {
        Stage stage = (Stage) editProductBtn.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProductCache productCache = ProductCache.getInstance();
        Product product = productCache.getProduct();
        nameField.setText(product.getName());
        codeField.setText(product.getCode());
        categoryField.setText(product.getCategory());
        positionField.setText(product.getPosition());
        priceField.setText(String.valueOf(product.getPrice()));
        quantityField.setText(String.valueOf(product.getQuantity()));
    }

}
