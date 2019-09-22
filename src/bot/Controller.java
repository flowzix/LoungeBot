package bot;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox loginStatusBox;

    @FXML
    private TextField inputLogin;

    @FXML
    private TextField inputPassword;

    @FXML
    private TableColumn<ShopItem, String> columnKeywords;

    @FXML
    private TableColumn<ShopItem, String> columnSize;

    @FXML
    private TableColumn<ShopItem, String> columnAnySize;

    @FXML
    private TableColumn<ShopItem, String> columnMaxPrice;

    @FXML
    private TableView<ShopItem> itemsTable;

    private Bot bot = new Bot();
    private ObservableList<ShopItem> userItems = FXCollections.observableArrayList();


    public Controller() {
        super();
    }

    public void onLoginClicked(MouseEvent mouseEvent) {
//        bot.loginUser(inputLogin.getText(), inputPassword.getText());
        ShopItem si = ShopItem.builder().keywords(new ArrayList<>()).maxPrice(120.2).size("M").chooseAnyIfNotAvailable(false).build();
        userItems.add(si);
    }

    public void itemWasAdded() {
        System.out.println("Item added");
    }


    public void onAddItemClicked(MouseEvent mouseEvent) throws Exception {
        Parent root;
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "ItemView.fxml"
                )
        );


        Stage stage = new Stage();
        stage.setTitle("Dodaj przedmiot");
        stage.setScene(new Scene(loader.load(), 450, 450));
        ItemController newWindowController = loader.getController();
        newWindowController.setParentController(this);
        stage.show();


//        ShopItem item = ShopItem.builder()
//                .keywords(Arrays.asList("a", "b"))
//                .size("S")
//                .chooseAnyIfNotAvailable(true)
//                .maxPrice(220.20)
//                .build();
//        userItems.add(item);
//
    }

    private void createBindings() {
        loginStatusBox.backgroundProperty().bind(Bindings.when(bot.getLoginProperty())
                .then(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)))
                .otherwise(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY))));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBindings();
        adjustColumnWidth();
        initTable();
    }

    private void initTable() {
        columnKeywords.setCellValueFactory(new PropertyValueFactory<>("keywordsForDisplay"));
        columnSize.setCellValueFactory(new PropertyValueFactory<>("sizeForDisplay"));
        columnAnySize.setCellValueFactory(new PropertyValueFactory<>("anySizeForDisplay"));
        columnMaxPrice.setCellValueFactory(new PropertyValueFactory<>("maxPriceForDisplay"));
        itemsTable.itemsProperty().setValue(userItems);
    }

    private void adjustColumnWidth() {
        columnKeywords.prefWidthProperty().bind(itemsTable.widthProperty().divide(2));
        columnSize.prefWidthProperty().bind(itemsTable.widthProperty().divide(6));
        columnAnySize.prefWidthProperty().bind(itemsTable.widthProperty().divide(6));
        columnMaxPrice.prefWidthProperty().bind(itemsTable.widthProperty().divide(6));
    }
}
