package bot.controller;

import bot.data.UserDefinedItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private Button buttonAddItem;
    private Controller parentController;

    @FXML
    private TextField inputKeywords;
    @FXML
    private TextField inputSize;
    @FXML
    private CheckBox inputAnySize;
    @FXML
    private TextField inputMaxPrice;
    @FXML
    private TextField inputCampaignID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addItemClicked(MouseEvent mouseEvent) {
        UserDefinedItem userDefinedItem = UserDefinedItem.builder()
                .keywords(Arrays.asList(inputKeywords.getText().trim().split(";")))
                .maxPrice(Double.parseDouble(inputMaxPrice.getText().trim().replace(',', '.')))
                .chooseAnyIfNotAvailable(inputAnySize.isSelected())
                .size(inputSize.getText().trim())
                .campaignID(inputCampaignID.getText().trim())
                .build();
        parentController.itemAddedAction(userDefinedItem);
        Stage stage = (Stage) buttonAddItem.getScene().getWindow();
        stage.close();
    }

    public void setParentController(Controller controller) {
        this.parentController = controller;
    }

}
