package bot;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox loginStatusBox;

    @FXML
    private TextField inputLogin;

    @FXML
    private TextField inputPassword;


    private Bot bot = new Bot();

    public Controller() {
        super();
    }

    public void onLoginClicked(MouseEvent mouseEvent) {
        bot.loginUser(inputLogin.getText(),inputPassword.getText());
    }

    private void createBindings() {
        loginStatusBox.backgroundProperty().bind(Bindings.when(bot.getLoginProperty())
                .then(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)))
                .otherwise(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY))));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBindings();
    }
}
