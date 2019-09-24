package bot.controller;

import bot.data.UserDefinedItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    @Setter
    @Getter
    private boolean editMode = false;

    private UserDefinedItem item;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addItemClicked(MouseEvent mouseEvent) {
        List<String> validationErrors = getValidationErrors();
        if (!validationErrors.isEmpty()) {
            String error = validationErrors.stream().collect(Collectors.joining());
            Alert alert = new Alert(Alert.AlertType.ERROR, error, ButtonType.OK);
            alert.showAndWait();
            return;
        }
        UserDefinedItem userDefinedItem = buildUserDefinedItem();
        if (editMode) {
            parentController.itemEditedAction(item, userDefinedItem);
        } else {
            parentController.itemAddedAction(userDefinedItem, this);
        }
        Stage stage = (Stage) buttonAddItem.getScene().getWindow();
        stage.close();
        setEditMode(false);
    }

    public void setEditedItem(UserDefinedItem item) {
        this.item = item;
        inputKeywords.setText(item.getKeywordsForDisplay());
        inputMaxPrice.setText(item.getMaxPriceForDisplay());
        inputAnySize.selectedProperty().set(item.isChooseAnyIfNotAvailable());
        inputSize.setText(item.getSize());
        inputCampaignID.setText(item.getCampaignID());
    }

    private UserDefinedItem buildUserDefinedItem() {
        return UserDefinedItem.builder()
                .keywords(Arrays.asList(inputKeywords.getText().trim().split(";")))
                .maxPrice(Double.parseDouble(inputMaxPrice.getText().trim().replace(',', '.')))
                .chooseAnyIfNotAvailable(inputAnySize.isSelected())
                .size(inputSize.getText().trim())
                .campaignID(inputCampaignID.getText().trim())
                .build();
    }

    public void setParentController(Controller controller) {
        this.parentController = controller;
    }

    private List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        boolean isAnyFieldEmpty =
                inputKeywords.getText().isEmpty() ||
                        inputMaxPrice.getText().isEmpty() ||
                        inputCampaignID.getText().isEmpty() ||
                        inputSize.getText().isEmpty();
        if (isAnyFieldEmpty) {
            errors.add("Wszystkie pola muszą zostać wypełnione!\n");
        }
        if (!inputMaxPrice.getText().trim().replace(',', '.').matches("([0-9]*[.])?[0-9]+")) {
            errors.add("Niepoprawny format kwoty! Kwota powinna być w formacie xxxx.xx lub xxxx.\n");
        }
        if (!inputCampaignID.getText().trim().matches("[0-9a-zA-Z]*")) {
            errors.add("Niepoprawny numer kampanii! Numer kampanii składa się tylko z liczb i liter.\n");
        }
        return errors;
    }
}
