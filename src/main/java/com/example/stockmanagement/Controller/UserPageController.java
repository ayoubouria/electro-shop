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
import com.example.stockmanagement.Model.Database.UserDB;
import com.example.stockmanagement.Model.Users.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
import javafx.util.Callback;

import static atlantafx.base.theme.Styles.BUTTON_ICON;
import static atlantafx.base.theme.Styles.ACCENT;
import static atlantafx.base.theme.Styles.BUTTON_OUTLINED;
import static atlantafx.base.theme.Styles.DANGER;

public class UserPageController implements Initializable{
    Scene addUserScene;

    private void setAddUserScene() throws IOException {
        Main.addUserLoader.setRoot(null);
        Main.addUserLoader.setController(null);
        Parent addUserParent = Main.addUserLoader.load();
        addUserScene = new Scene(addUserParent);
    }

    ObservableList<User> data;

    @FXML
    private TableColumn<User, Integer> IdColumn;

    @FXML
    private TableColumn<User,Void> actionsColumn;

    @FXML
    private Button addBtn;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> dateColumn;

    @FXML
    private TableColumn<User, String> firstColumn;

    @FXML
    private TableColumn<User, Boolean> isAdminColumn;

    @FXML
    private TableColumn<User, String> lastColumn;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private Button refetchBtn;

    private void fetchUsers() throws SQLException {
        UserDB userDB = new UserDB();
        ArrayList<User> users = userDB.selectAllUsers();
        data = FXCollections.observableArrayList(users);
        userTableView.setItems(data);
    }

    @FXML
    void refetch(MouseEvent event) throws SQLException {
        fetchUsers();
    }

    @FXML
    void addUser(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        if (addUserScene == null) {
            setAddUserScene();
        }
        stage.setScene(addUserScene);
        stage.setTitle("Add Product");
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refetchBtn.setGraphic(new FontIcon(Feather.FTH_ROTATE_CCW));
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        isAdminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        actionsColumn.setCellFactory(new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                HBox hbox = new HBox();
                // Button editButton = new Button("", new FontIcon(Feather.FTH_EDIT));
                Button deleteButton = new Button("", new FontIcon(Feather.FTH_TRASH));
                // editButton.getStyleClass().addAll(BUTTON_ICON, ACCENT);
                deleteButton.getStyleClass().addAll(BUTTON_ICON, BUTTON_OUTLINED, DANGER);
                // editButton.setPrefWidth(35);
                deleteButton.setPrefWidth(35);
                hbox.getChildren().addAll(deleteButton);
                hbox.setMargin(deleteButton, new javafx.geometry.Insets(0, 5, 0, 9));
                // hbox.setMargin(editButton, new javafx.geometry.Insets(0, 0, 0, 9));
                TableCell<User, Void> cell = new TableCell<User, Void>() {
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

                deleteButton.setOnAction(event -> {
                    User user = cell.getTableView().getItems().get(cell.getIndex());
                    data.remove(user);
                    UserDB userDB = new UserDB();
                    var alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Delte User");
                    alert.setContentText("Are you sure you want to delete " + user.getUsername());
                    alert.initOwner(addBtn.getScene().getWindow());
                    ButtonType okBtn = new ButtonType("Ok", ButtonData.APPLY);
                    ButtonType cancelBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(okBtn, cancelBtn);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (!result.isPresent()) {
                    } else if (result.get() == okBtn) {
                        try {
                            userDB.deleteUser(user);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        data.remove(user);

                    } 
                });
                return cell;
            }
        });

        try {
            fetchUsers();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        addBtn.setGraphic(new FontIcon(Feather.FTH_PLUS));


    }

}
