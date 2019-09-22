package bot;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private Button buttonAddItem;
    private Controller parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addItemClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) buttonAddItem.getScene().getWindow();
        parentController.itemWasAdded();
        stage.close();
    }

    public void setParentController(Controller controller) {
        this.parentController = controller;
    }

}
