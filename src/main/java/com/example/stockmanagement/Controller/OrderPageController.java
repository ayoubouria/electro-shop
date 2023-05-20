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
import com.example.stockmanagement.Cache.OrderCache;
import com.example.stockmanagement.Model.Classes.Client;
import com.example.stockmanagement.Model.Classes.Order;
import com.example.stockmanagement.Model.Classes.Product;
import com.example.stockmanagement.Model.Classes.Pdf;
import com.example.stockmanagement.Model.Database.ClientsDB;
import com.example.stockmanagement.Model.Database.OrdersDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import static atlantafx.base.theme.Styles.BUTTON_ICON;
import static atlantafx.base.theme.Styles.ACCENT;
import static atlantafx.base.theme.Styles.BUTTON_OUTLINED;
import static atlantafx.base.theme.Styles.DANGER;
import static atlantafx.base.theme.Styles.SUCCESS;

public class OrderPageController implements Initializable {
    ObservableList<Order> data;

    @FXML
    private TableColumn<Order, Integer> IdColumn;

    @FXML
    private TableColumn<Order, Void> actionsColumn;

    @FXML
    private Button addBtn;

    @FXML
    private TableColumn<Order, String> clientColumn;

    @FXML
    private TableColumn<Order, String> dateColumn;

    @FXML
    private TableView<Order> orderTableView;

    @FXML
    private TableColumn<Order, Integer> priceColumn;

    @FXML
    private TableColumn<Order, ArrayList<Product>> productColumn;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Button refetchBtn;

    private void fetchOrders() throws SQLException {
        OrdersDB orderDB = new OrdersDB();
        ArrayList<Order> orders = orderDB.selectAllOrders();
        data = FXCollections.observableArrayList(orders);
        orderTableView.setItems(data);
    }

    @FXML
    void refetch(MouseEvent event) throws SQLException {
        fetchOrders();
    }

    @FXML
    void addOrder(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Main.addOrderLoader.setRoot(null);
        Main.addOrderLoader.setController(null);
        stage.setScene(new Scene(Main.addOrderLoader.load()));
        stage.setTitle("Add Product");
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refetchBtn.setGraphic(new FontIcon(Feather.FTH_ROTATE_CCW));
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("oid"));
        clientColumn.setCellFactory(new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
            @Override
            public TableCell<Order, String> call(TableColumn<Order, String> param) {
                TableCell<Order, String> cell = new TableCell<Order, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            ClientsDB clientDB = new ClientsDB();
                            Order order = getTableView().getItems().get(getIndex());
                            try {
                                Client client = clientDB.selectClient(order.getClientId());
                                // Label clientNameLabel = new Label(client.getName());
                                // setGraphic(clientNameLabel);
                                setText(client.getName());
                            } catch (SQLException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            
                        }
                    }
                };
                return cell;
            }
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("productsList"));
        actionsColumn.setCellFactory(new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
            @Override
            public TableCell<Order, Void> call(TableColumn<Order, Void> param) {
                HBox hbox = new HBox();
                Button editButton = new Button("", new FontIcon(Feather.FTH_EDIT));
                Button deleteButton = new Button("", new FontIcon(Feather.FTH_TRASH));
                Button confirmButton = new Button("", new FontIcon(Feather.FTH_CHECK));
                editButton.getStyleClass().addAll(BUTTON_ICON, ACCENT);
                deleteButton.getStyleClass().addAll(BUTTON_ICON, BUTTON_OUTLINED, DANGER);
                confirmButton.getStyleClass().addAll(BUTTON_ICON, BUTTON_OUTLINED, SUCCESS);
                editButton.setPrefWidth(35);
                deleteButton.setPrefWidth(35);
                confirmButton.setPrefWidth(35);
                TableCell<Order, Void> cell = new TableCell<Order, Void>() {
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            OrdersDB orderDB = new OrdersDB();
                            Order order = getTableView().getItems().get(getIndex());
                            try {
                                if (orderDB.checkConfirmOrder(order.getOid())) {
                                    // System.out.println(orderDB.checkConfirmOrder(order.getOid()));
                                    // confirmButton.setVisible(false);
                                    // editButton.setVisible(false);
                                    // hbox.getChildren().removeAll(editButton, confirmButton);
                                    hbox.getChildren().clear();
                                    hbox.getChildren().addAll(deleteButton);
                                    hbox.setMargin(deleteButton, new javafx.geometry.Insets(0, 5, 0, 9));
                                } 
                                else{
                                    hbox.getChildren().clear();
                                    hbox.getChildren().addAll(editButton, deleteButton, confirmButton);
                                    hbox.setMargin(deleteButton, new javafx.geometry.Insets(0, 5, 0, 9));
                                    hbox.setMargin(editButton, new javafx.geometry.Insets(0, 0, 0, 9));
                                    hbox.setMargin(confirmButton, new javafx.geometry.Insets(0, 0, 0, 9));
                                }
                            } catch (SQLException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            setGraphic(hbox);

                        }
                    }
                };

                editButton.setOnAction(event -> {
                    Order order = cell.getTableView().getItems().get(cell.getIndex());
                    // System.out.println("edit " + order);
                    OrderCache orderCache = OrderCache.getInstance();
                    orderCache.setOrder(order);
                    Stage stage = new Stage();
                    try{
                        Main.editOrderLoader.setRoot(null);
                        Main.editOrderLoader.setController(null);
                        stage.setScene(new Scene(Main.editOrderLoader.load()));
                        stage.setTitle("Edit Order");
                        stage.show();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                });

                deleteButton.setOnAction(event -> {
                    Order order = cell.getTableView().getItems().get(cell.getIndex());
                    OrdersDB orderDB = new OrdersDB();
                    var alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Delte Order");
                    alert.setContentText("Are you sure you want to delete order number" + order.getOid() + " ?");
                    alert.initOwner(addBtn.getScene().getWindow());
                    ButtonType okBtn = new ButtonType("Ok", ButtonData.APPLY);
                    ButtonType cancelBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(okBtn, cancelBtn);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (!result.isPresent()) {
                    } else if (result.get() == okBtn) {
                        try {
                            orderDB.deleteOrder(order.getOid());
                            data.remove(order);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                confirmButton.setOnAction(event -> {
                    Order order = cell.getTableView().getItems().get(cell.getIndex());
                    // System.out.println("confirm " + order);
                    OrdersDB orderDB = new OrdersDB();
                    var alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Confirm Order");
                    alert.setContentText("Are you sure you want to confirm order number" + order.getOid() + " ?");
                    alert.initOwner(addBtn.getScene().getWindow());
                    ButtonType okBtn = new ButtonType("Ok", ButtonData.APPLY);
                    ButtonType cancelBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(okBtn, cancelBtn);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (!result.isPresent()) {
                    } else if (result.get() == okBtn) {
                        try {
                            orderDB.confirmOrder(order.getOid());
                            orderDB.delteProductQuantity(order);
                            Pdf.creatReceipt(order);
                            fetchOrders();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return cell;
            }
        });

        try {
            fetchOrders();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        addBtn.setGraphic(new FontIcon(Feather.FTH_PLUS));
        orderTableView.getSortOrder().add(dateColumn);
        dateColumn.setSortType(TableColumn.SortType.DESCENDING);


    }

}