package bot.logic;

import bot.data.ShopDefinedItem;
import bot.data.UserDefinedItem;
import bot.json.generated.ItemPageResponse;
import bot.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Bot {
    @Getter
    private BooleanProperty loginProperty = new SimpleBooleanProperty(false);

    public Bot() {

    }

    public void loginUser(String email, String password) {
        loginProperty.set(BotRequests.sendLoginRequest(email, password));
    }
    public void startBot(List<UserDefinedItem> userItems){
        List<ItemPageResponse> items = getItemsFromCampaign(userItems.get(0).getCampaignID());
        List<ShopDefinedItem> parsedItems = ResponseParser.parseItemPageResponses(items);
    }
    public List<ItemPageResponse> getItemsFromCampaign(String campaignID) {
        List<ItemPageResponse> itemPageResponses = new ArrayList<>();
        ItemPageResponse unparsedItems = BotRequests.getItemsFromPage(campaignID, 0);
        if(unparsedItems.getPagination().getTotalArticles() == 0){
            Logger.log("Oczekiwanie na rozpoczęcie kampanii " + campaignID);
            return null;
        }
        Logger.log("Ladowanie " + unparsedItems.getPagination().getTotalArticles().toString() + " przedmiotów z kampanii nr " + campaignID);
        itemPageResponses.add(unparsedItems);
        for (int i = 1; i < unparsedItems.getPagination().getTotalPages(); i++) {
            itemPageResponses.add(BotRequests.getItemsFromPage(campaignID, i));
        }
        return itemPageResponses;
    }

    public boolean isUserLogged() {
        return loginProperty.get();
    }

}
