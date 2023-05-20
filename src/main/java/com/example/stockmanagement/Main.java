package com.example.stockmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    public static FXMLLoader  HelloLoader = new FXMLLoader(Main.class.getResource("View/home-page.fxml"));
    public static FXMLLoader HomePageLoader = new FXMLLoader(Main.class.getResource("View/home-page-1.fxml"));
    public static FXMLLoader productLoader = new FXMLLoader(Main.class.getResource("View/products-page.fxml"));
    public static FXMLLoader clientLoader = new FXMLLoader(Main.class.getResource("View/clients-page.fxml"));
    public static FXMLLoader userLoader =  new FXMLLoader(Main.class.getResource("View/users-page.fxml"));
    public static FXMLLoader orderLoader = new FXMLLoader(Main.class.getResource("View/orders-page.fxml"));
    public static FXMLLoader welcomeLoader = new FXMLLoader(Main.class.getResource("View/welcome-page.fxml"));
    public static FXMLLoader addProductLoader = new FXMLLoader(Main.class.getResource("View/add-product.fxml"));
    public static FXMLLoader editProductLoader = new FXMLLoader(Main.class.getResource("View/edit-product.fxml"));
    public static FXMLLoader editUserLoader = new FXMLLoader(Main.class.getResource("View/edit-user.fxml"));
    public static FXMLLoader addUserLoader = new FXMLLoader(Main.class.getResource("View/add-user.fxml"));
    public static FXMLLoader addClientLoader = new FXMLLoader(Main.class.getResource("View/add-client.fxml"));
    public static FXMLLoader editClientLoader = new FXMLLoader(Main.class.getResource("View/edit-client.fxml"));
    public static FXMLLoader addOrderLoader = new FXMLLoader(Main.class.getResource("View/add-order.fxml"));
    public static FXMLLoader editOrderLoader = new FXMLLoader(Main.class.getResource("View/edit-order.fxml"));
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Loader" + HelloLoader.getLocation());
        changeTheme("primer-dark.css");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("View/welcome-page.fxml"));
        System.out.println("CLass" + Main.class);
        Scene scene = new Scene(fxmlLoader.load(), 1650, 1000);
        //delete the scene edges
        stage.initStyle(StageStyle.UNDECORATED);   
        stage.setResizable(false);     
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public static void changeTheme(String theme) {
        Application.setUserAgentStylesheet(theme);
    }
}