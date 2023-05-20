package com.example.stockmanagement.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.util.Callback;

import com.example.stockmanagement.Main;
import com.example.stockmanagement.Cache.ClientCache;
import com.example.stockmanagement.Model.Classes.Client;
import com.example.stockmanagement.Model.Database.ClientsDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static atlantafx.base.theme.Styles.BUTTON_ICON;
import static atlantafx.base.theme.Styles.ACCENT;
import static atlantafx.base.theme.Styles.BUTTON_OUTLINED;
import static atlantafx.base.theme.Styles.DANGER;
public class ClientPageController implements Initializable{
    ObservableList<Client> data;


    @FXML
    private TableColumn<Client, Integer> IdColumn;

    @FXML
    private TableColumn<Client, Void> actionsColumn;

    @FXML
    private Button addBtn;

    @FXML
    private TableColumn<Client, String> addresseColumn;

    @FXML
    private TableView<Client> clientTableView;

    @FXML
    private TableColumn<Client, String> emailColumn;

    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Button refetchBtn;

    @FXML
    void addClient(ActionEvent event) throws IOException {
        Main.addClientLoader.setRoot(null);
        Main.addClientLoader.setController(null);
        Stage stage = new Stage();
        stage.setScene(new Scene(Main.addClientLoader.load()));
        stage.setTitle("Add Client");
        stage.show();
    }
    private void fetchClientDB() throws SQLException {
        ClientsDB clientDB = new ClientsDB();
        ArrayList<Client> clients = clientDB.selectAllClients();
        data = FXCollections.observableArrayList(clients);
        clientTableView.setItems(data);
    }
    @FXML
    void refetch(MouseEvent event) throws SQLException {
        fetchClientDB();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refetchBtn.setGraphic(new FontIcon(Feather.FTH_ROTATE_CCW));
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("cid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addresseColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        actionsColumn.setCellFactory(new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
            @Override
            public TableCell<Client, Void> call(TableColumn<Client, Void> param) {
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
                TableCell<Client, Void> cell = new TableCell<Client, Void>() {
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
                    Client client = cell.getTableView().getItems().get(cell.getIndex());
                    ClientCache clientCache = ClientCache.getInstance();
                    clientCache.setClient(client);
                    Stage stage = new Stage();
                    try {
                        Main.editClientLoader.setRoot(null);
                        Main.editClientLoader.setController(null);
                        stage.setScene(new Scene(Main.editClientLoader.load()));
                        stage.setTitle("Edit Client");
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

                deleteButton.setOnAction(event -> {
                    ClientsDB clientDB = new ClientsDB();
                    Client client = cell.getTableView().getItems().get(cell.getIndex());
                    var alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Delte Client");
                    alert.setContentText("Are you sure you wanr to delete " + client.getName());
                    alert.initOwner(addBtn.getScene().getWindow());
                    ButtonType okBtn = new ButtonType("Ok", ButtonData.APPLY);
                    ButtonType cancelBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(okBtn, cancelBtn);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (!result.isPresent()) {
                    } else if (result.get() == okBtn) {
                        try {
                            clientDB.deleteClient(client.getCid());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        data.remove(client);
                    } 
                });
                return cell;
            }
        });
        try {
            fetchClientDB();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        addBtn.setGraphic(new FontIcon(Feather.FTH_PLUS));
        

        
        
    }

}
