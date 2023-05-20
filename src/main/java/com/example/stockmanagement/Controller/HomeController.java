package com.example.stockmanagement.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.stockmanagement.Model.Database.ClientsDB;
import com.example.stockmanagement.Model.Database.OrdersDB;
import com.example.stockmanagement.Model.Database.ProductDB;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class HomeController implements Initializable {

    @FXML
    private Label accLabel;

    @FXML
    private Label consoleLabel;

    @FXML
    private Label gamesLabel;

    @FXML
    private Label laptopLabel;

    @FXML
    private Label mobileLabel;

    @FXML
    private Label productLabel;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label softwareLabel;

    @FXML
    private Label tabletLabel;

    @FXML
    private Label clientLabel;
    @FXML
    private Label orderLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProductDB productDB = new ProductDB();
        ClientsDB clientDB = new ClientsDB();
        OrdersDB ordersDB = new OrdersDB();
        try {
            String productCount = productDB.productCount() + "";
            String accCount = productDB.productCountByCategory("Accessories") + "";
            String consoleCount = productDB.productCountByCategory("Consoles") + "";
            String gamesCount = productDB.productCountByCategory("Games") + "";
            String laptopCount = productDB.productCountByCategory("Laptops") + "";
            String mobileCount = productDB.productCountByCategory("Mobiles") + "";
            String softwareCount = productDB.productCountByCategory("Software") + "";
            String tabletCount = productDB.productCountByCategory("Tablets") + "";
            String clientCount = clientDB.clientCount() + "";
            String orderCount = ordersDB.ordersCount()+"";
            productLabel.setText(productCount);
            accLabel.setText(accCount);
            consoleLabel.setText(consoleCount);
            gamesLabel.setText(gamesCount);
            laptopLabel.setText(laptopCount);
            mobileLabel.setText(mobileCount);
            softwareLabel.setText(softwareCount);
            tabletLabel.setText(tabletCount);
            clientLabel.setText(clientCount);
            orderLabel.setText(orderCount);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
