package com.example.stockmanagement.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import com.example.stockmanagement.Main;
import com.example.stockmanagement.Cache.ProductCache;
import com.example.stockmanagement.Model.Classes.Product;
import com.example.stockmanagement.Model.Database.ProductDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import static atlantafx.base.theme.Styles.BUTTON_ICON;
import static atlantafx.base.theme.Styles.ACCENT;
import static atlantafx.base.theme.Styles.BUTTON_OUTLINED;
import static atlantafx.base.theme.Styles.DANGER;

public class ProductPageController implements Initializable {
    Scene addProductScene;

    private void setAddProductScene() throws IOException {
        Parent addProductParent = Main.addProductLoader.load();
        addProductScene = new Scene(addProductParent);
    }

    Scene editProductScene;

    private void setEditProductScene() throws IOException {
        Main.editProductLoader.setRoot(null);
        Main.editProductLoader.setController(null);
        Parent editProductParent = Main.editProductLoader.load();
        editProductScene = new Scene(editProductParent);
    }

    ObservableList<Product> data;
    @FXML
    private Button addBtn;

    @FXML
    private TableColumn<Product, Integer> IdColumn;

    @FXML
    private TableColumn<Product, Void> actionsColumn;

    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private TableColumn<Product, String> codeColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, String> positionColumn;

    @FXML
    private TableColumn<Product, Integer> priceColumn;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    @FXML
    private Button refetchBtn;

    private void fetchProducts() throws SQLException {
        ProductDB productDB = new ProductDB();
        ArrayList<Product> products = productDB.selectAllProducts();
        data = FXCollections.observableArrayList(products);
        productTableView.setItems(data);
    }

    @FXML
    void refetch(MouseEvent event) throws SQLException {
        fetchProducts();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refetchBtn.setGraphic(new FontIcon(Feather.FTH_ROTATE_CCW));
        ProductDB productDB = new ProductDB();
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
                Button editButton = new Button("", new FontIcon(Feather.FTH_EDIT));
                Button deleteButton = new Button("", new FontIcon(Feather.FTH_TRASH));
                editButton.getStyleClass().addAll(BUTTON_ICON, ACCENT);
                deleteButton.getStyleClass().addAll(BUTTON_ICON, BUTTON_OUTLINED, DANGER);
                editButton.setPrefWidth(35);
                deleteButton.setPrefWidth(35);
                hbox.getChildren().addAll(editButton, deleteButton);
                hbox.setMargin(deleteButton, new javafx.geometry.Insets(0, 5, 0, 9));
                hbox.setMargin(editButton, new javafx.geometry.Insets(0, 0, 0, 9));
                TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox);
                        }
                    }
                };

                editButton.setOnAction(event -> {
                    Product product = cell.getTableView().getItems().get(cell.getIndex());
                    ProductCache productCache = ProductCache.getInstance();
                    productCache.clearProduct();
                    productCache.setProduct(product);
                    // System.out.println(productCache.getProduct().getName() + " " + productCache.getProduct().getPid());
                    Stage stage = new Stage();
                    if (editProductScene == null) {
                        try {
                            setEditProductScene();
                            stage.setScene(editProductScene);
                            editProductScene = null;
                            stage.setTitle("Edit Product");
                            stage.show();

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } else {
                        stage.setScene(editProductScene);
                        editProductScene = null;
                        stage.setTitle("Edit Product");
                        stage.show();

                    }

                });

                deleteButton.setOnAction(event -> {
                    Product product = cell.getTableView().getItems().get(cell.getIndex());
                    var alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Delte Product");
                    alert.setContentText("Are you sure you wanr to delete " + product.getName());
                    alert.initOwner(addBtn.getScene().getWindow());
                    ButtonType okBtn = new ButtonType("Ok", ButtonData.APPLY);
                    ButtonType cancelBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(okBtn, cancelBtn);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (!result.isPresent()) {
                    } else if (result.get() == okBtn) {
                        System.out.println(product.getPid());
                        try {
                            productDB.deleteProduct(product.getPid());
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        data.remove(product);
                    } 
                });
                return cell;
            }
        });

        try {
            fetchProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // addIcon = new FontIcon(Feather.FTH_PLUS);
        // addIcon.getStyleClass().addAll(BUTTON_ICON, ACCENT);
        // addIcon.setIconSize(20);
        addBtn.setGraphic(new FontIcon(Feather.FTH_PLUS));
    }

    @FXML
    void addProduct(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        if (addProductScene == null) {
            setAddProductScene();
        }
        stage.setScene(addProductScene);
        stage.setTitle("Add Product");
        stage.show();
    }

}
