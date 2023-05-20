package com.example.stockmanagement.Controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import com.example.stockmanagement.Model.Classes.Client;
import com.example.stockmanagement.Model.Classes.Order;
import com.example.stockmanagement.Model.Classes.Product;
import com.example.stockmanagement.Model.Database.ClientsDB;
import com.example.stockmanagement.Model.Database.OrdersDB;
import com.example.stockmanagement.Model.Database.ProductDB;

import atlantafx.base.controls.CustomTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import static atlantafx.base.theme.Styles.BUTTON_ICON;
import static atlantafx.base.theme.Styles.ACCENT;
import static atlantafx.base.theme.Styles.DANGER;

public class AddOrderController implements Initializable {
    ObservableList<Product> data;
    ObservableMap<Product, Integer> productAndQuantities;
    @FXML
    private TableColumn<Product, Integer> IdColumn;

    @FXML
    private TableColumn<Product, Void> actionsColumn;

    @FXML
    private Button addProductBtn;

    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private TableColumn<Product, String> codeColumn;

    @FXML
    private CustomTextField codeField;

    @FXML
    private Label errorLabel;

    @FXML
    private TableColumn<Product, String> nameColumn;

    // @FXML
    // private CustomTextField clientNameField;

    @FXML
    private TableColumn<Product, String> positionColumn;

    @FXML
    private TableColumn<Product, Integer> priceColumn;

    @FXML
    private TableView<Product> productView;

    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    @FXML
    private Button returnBtn;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ComboBox<String> clientsSelection;

    private void clearFields() {
        clientsSelection.getSelectionModel().clearSelection();
        productAndQuantities.clear();
    }


    @FXML
    void addOrder(ActionEvent event) throws SQLException {
        String clientName = clientsSelection.getValue();
        if (clientName == null && productAndQuantities.isEmpty()) {
            errorLabel.setText("Please enter Client Name and add at least one product");
            errorLabel.setTextFill(Color.RED);
        } else if(productAndQuantities.isEmpty() && clientName != null) {
            errorLabel.setText("Please add at least one product");
            errorLabel.setTextFill(Color.RED);
        } else if (clientName == null && !productAndQuantities.isEmpty()) {
            errorLabel.setText("Please enter Client Name");
            errorLabel.setTextFill(Color.RED);
        }
        else {
            LocalDate date = LocalDate.now();
            String dateCreated = date.toString();
            ClientsDB clientDB = new ClientsDB();
            int clientId = clientDB.getClientId(clientName);
            HashMap <Product, Integer> productData = new HashMap<>();
            productData.putAll(productAndQuantities);
            Order order = new Order(clientId, productData, dateCreated);
            OrdersDB ordersDB = new OrdersDB();
            ordersDB.insertOrder(order);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Order Added");
            alert.setHeaderText(null);
            alert.setContentText("Order added successfully");
            alert.showAndWait();
            clearFields();
        }
    }


    @FXML
    void showOrderPage(MouseEvent event) {
        Stage stage = (Stage) returnBtn.getScene().getWindow();
        stage.close();
    }


    private void fetchProducts() throws SQLException {
        ProductDB productDB = new ProductDB();
        ArrayList<Product> products = productDB.selectAllProducts();
        data = FXCollections.observableArrayList(products);
        productView.setItems(data);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProductDB productDB = new ProductDB();
        productAndQuantities = FXCollections.observableHashMap();
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        // add button to the table with callback
        actionsColumn.setCellFactory(new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(TableColumn<Product, Void> param) {
                HBox hbox = new HBox();
                Button addButton = new Button("", new FontIcon(Feather.FTH_PLUS));
                Button removeButton = new Button("", new FontIcon(Feather.FTH_MINUS));
                addButton.getStyleClass().addAll(BUTTON_ICON, ACCENT);
                removeButton.getStyleClass().addAll(BUTTON_ICON, DANGER);
                addButton.setPrefWidth(35);
                removeButton.setPrefWidth(35);
                hbox.getChildren().addAll(addButton);
                hbox.setMargin(addButton, new javafx.geometry.Insets(0, 0, 0, 9));
                hbox.setMargin(removeButton, new javafx.geometry.Insets(0, 0, 0, 9));
                TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Product product = getTableView().getItems().get(getIndex());
                            if (productAndQuantities.containsKey(product)) {
                                hbox.getChildren().clear();
                                hbox.getChildren().addAll(removeButton);
                            } else {
                                hbox.getChildren().clear();
                                hbox.getChildren().addAll(addButton);
                            }
                            setGraphic(hbox);
                        }
                    }
                };
                addButton.setOnAction(event -> {
                    // System.out.println(clients.getSelectionModel().getSelectedItem());
                    Product product = productView.getItems().get(cell.getIndex());
                    TextInputDialog dialog = new TextInputDialog("1");
                    dialog.setTitle("Quantité");
                    dialog.setHeaderText("Quantité de " + product.getName());
                    dialog.setContentText("Veuillez entrer la quantité de " + product.getName() + " à ajouter à la commande:");

                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(quantity -> {
                        try {
                            int q = Integer.parseInt(quantity);
                            if (q > 0 && q <= product.getQuantity()) {
                                productAndQuantities.put(product, q);
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Information");
                                alert.setHeaderText(null);
                                alert.setContentText("Le produit " + product.getName() + " a été ajouté à la commande avec une quantité de " + q);
                                alert.showAndWait();
                                // System.out.println(productAndQuantities);
                                hbox.getChildren().clear();
                                hbox.getChildren().addAll(removeButton);
                            } else if(q > product.getQuantity()) {
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Erreur");
                                alert.setHeaderText(null);
                                alert.setContentText("La quantité doit être inférieure ou égale à " + product.getQuantity());
                                alert.showAndWait();
                                // System.out.println(productAndQuantities);
                            }
                            else {
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Erreur");
                                alert.setHeaderText(null);
                                alert.setContentText("La quantité doit être supérieure à 0");
                                alert.showAndWait();
                            }
                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Erreur");
                            alert.setHeaderText(null);
                            alert.setContentText("La quantité doit être un nombre entier");
                            alert.showAndWait();
                        }
                    });
                });
                removeButton.setOnAction(event -> {
                    Product product = productView.getItems().get(cell.getIndex());
                    productAndQuantities.remove(product);
                    hbox.getChildren().clear();
                    hbox.getChildren().addAll(addButton);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Le produit " + product.getName() + " a été retiré de la commande");
                    alert.showAndWait();
                });

                return cell;
            }
        });
        ClientsDB clientDB = new ClientsDB();
        try {
            ArrayList<Client> clientsList = clientDB.selectAllClients();
            for(Client client : clientsList)
            {
                clientsSelection.getItems().add(client.getName());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            fetchProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
