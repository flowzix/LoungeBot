package bot.logic;

import bot.json.generated.JSON;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

public class Bot {
    @Getter
    private BooleanProperty loginProperty = new SimpleBooleanProperty(false);

    public Bot() {

    }

    public void loginUser(String email, String password) {
        loginProperty.set(BotRequests.sendLoginRequest(email, password));
    }
    public void getItemsFromCampaign(String url){
        JSON unparsedItems = BotRequests.getItemPageRequest(url);
    }

}
