package bot;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox loginStatusBox;

    @FXML
    private TextField inputLogin;

    @FXML
    private TextField inputPassword;

    @FXML
    private TableColumn columnKeywords;

    @FXML
    private TableColumn columnSize;

    @FXML
    private TableColumn columnAnySize;

    @FXML
    private TableColumn columnMaxPrice;

    @FXML
    private TableView itemsTable;

    private Bot bot = new Bot();

    public Controller() {
        super();
    }

    public void onLoginClicked(MouseEvent mouseEvent) {
        bot.loginUser(inputLogin.getText(), inputPassword.getText());
    }

    public void onAddItemClicked(MouseEvent mouseEvent) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("ItemView.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Dodaj przedmiot");
        stage.setScene(new Scene(root, 450, 450));
        stage.show();
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
    }

    private void adjustColumnWidth() {
        columnKeywords.prefWidthProperty().bind(itemsTable.widthProperty().divide(2));
        columnSize.prefWidthProperty().bind(itemsTable.widthProperty().divide(6));
        columnAnySize.prefWidthProperty().bind(itemsTable.widthProperty().divide(6));
        columnMaxPrice.prefWidthProperty().bind(itemsTable.widthProperty().divide(6));
    }

}
