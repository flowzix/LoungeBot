package bot.controller;

import bot.data.UserDefinedItem;
import bot.logging.Logger;
import bot.logic.Bot;
import com.thoughtworks.xstream.XStream;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox loginStatusBox;

    @FXML
    private TextField inputLogin;

    @FXML
    private TextField inputPassword;

    @FXML
    private TableColumn<UserDefinedItem, String> columnKeywords;

    @FXML
    private TableColumn<UserDefinedItem, String> columnSize;

    @FXML
    private TableColumn<UserDefinedItem, String> columnAnySize;

    @FXML
    private TableColumn<UserDefinedItem, String> columnMaxPrice;

    @FXML
    private TableView<UserDefinedItem> itemsTable;

    @FXML
    private TableColumn<UserDefinedItem, String> columnCampaignID;

    @FXML
    private TextArea logArea;

    private Bot bot = new Bot();
    private ObservableList<UserDefinedItem> userItems;

    private Thread botThread = null;

    public Controller() {
        super();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBindings();
        adjustColumnWidth();
        initTable();
        Logger.initialize(logArea);
    }

    public void onLoginClicked(MouseEvent mouseEvent) {
        new Thread(() -> loginUser()).start();
    }

    private void loginUser() {
        bot.loginUser(inputLogin.getText(), inputPassword.getText());
        if (bot.isUserLogged()) {
            Logger.log("Zalogowano pomyślnie");
        } else {
            Logger.log("Logowanie nie powiodło się.");
        }
    }


    public void itemAddedAction(UserDefinedItem userDefinedItem, ItemController itemController) {
        if (!itemController.isEditMode()) {
            userItems.add(userDefinedItem);
        }
    }

    public void itemEditedAction(UserDefinedItem oldItem, UserDefinedItem newItem) {
        userItems.remove(oldItem);
        userItems.add(newItem);
    }

    public void onBotStopClicked(MouseEvent mouseEvent) throws Exception {
        botThread.interrupt();
    }

    public void onAddItemClicked(MouseEvent mouseEvent) throws Exception {
        showItemWindow().setParentController(this);
    }

    public void onStartBotClicked(MouseEvent mouseEvent) {
        if(botThread != null && botThread.isAlive()){
            Logger.log("Bot już działa.");
            return;
        }
        if (bot.isUserLogged()) {
            List<UserDefinedItem> copiedItems = new ArrayList(userItems);
            botThread = new Thread(() -> bot.startBot(copiedItems));
            botThread.start();
        } else {
            Logger.log("Musisz być zalogowany.");
        }
    }


    public void onEditItemClicked(MouseEvent mouseEvent) throws Exception {
        if (itemsTable.getSelectionModel().getSelectedItem() != null) {
            ItemController itemController = showItemWindow();
            itemController.setParentController(this);
            itemController.setEditMode(true);
            itemController.setEditedItem(itemsTable.getSelectionModel().getSelectedItem());
        }
    }

    private ItemController showItemWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ItemView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Edycja przedmiotu");
        stage.setScene(new Scene(loader.load(), 450, 450));
        ItemController newWindowController = loader.getController();
        stage.show();
        return newWindowController;
    }

    public void onRemoveItemClicked(MouseEvent mouseEvent) {
        userItems.remove(itemsTable.getSelectionModel().getSelectedItem());
    }

    private void createBindings() {
        loginStatusBox.backgroundProperty().bind(
                Bindings.when(bot.getLoginProperty())
                        .then(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)))
                        .otherwise(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY))
                        )
        );
    }

    private void initTable() {
        columnKeywords.setCellValueFactory(new PropertyValueFactory<>("keywordsForDisplay"));
        columnSize.setCellValueFactory(new PropertyValueFactory<>("sizeForDisplay"));
        columnAnySize.setCellValueFactory(new PropertyValueFactory<>("anySizeForDisplay"));
        columnMaxPrice.setCellValueFactory(new PropertyValueFactory<>("maxPriceForDisplay"));
        columnCampaignID.setCellValueFactory(new PropertyValueFactory<>("campaignIDForDisplay"));
        if (new File("items.xml").exists()) {
            loadItemsFromXML();
        } else {
            userItems = FXCollections.observableArrayList();
        }
        itemsTable.itemsProperty().setValue(userItems);
    }

    private void adjustColumnWidth() {
        columnKeywords.prefWidthProperty().bind(itemsTable.widthProperty().divide(1.0 / (3.0 / 8)));
        columnSize.prefWidthProperty().bind(itemsTable.widthProperty().divide((1.0 / (1.0 / 8))));
        columnAnySize.prefWidthProperty().bind(itemsTable.widthProperty().divide((1.0 / (1.0 / 8))));
        columnMaxPrice.prefWidthProperty().bind(itemsTable.widthProperty().divide(1.0 / (1.0 / 8)));
        columnCampaignID.prefWidthProperty().bind(itemsTable.widthProperty().divide(1.0 / (2.0 / 8)));
    }

    public void saveUserItemsToXML() {
        XStream xs = new XStream();
        try (FileWriter fw = new FileWriter("items.xml")) {
            fw.write(xs.toXML(new ArrayList<>(userItems)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadItemsFromXML() {
        XStream xs = new XStream();
        List<UserDefinedItem> loadedUserItems = (List<UserDefinedItem>) xs.fromXML(new File("items.xml"));
        userItems = FXCollections.observableArrayList(loadedUserItems);
    }
}
