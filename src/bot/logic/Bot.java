package bot.logic;

import bot.data.ShopDefinedItem;
import bot.data.ShopDefinedItemVariant;
import bot.data.UserDefinedItem;
import bot.json.generated.itempage.ItemPageResponse;
import bot.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static bot.constant.BotConfig.SLEEP_BETWEEN_CAMPAIGN_LOAD_TRIRES;

public class Bot {
    @Getter
    private BooleanProperty loginProperty = new SimpleBooleanProperty(false);
    private Map<String, List<ShopDefinedItem>> campaignIDToItems = new HashMap<>(); // a map to reuse campaign items

    public Bot() {

    }

    public void loginUser(String email, String password) {
        loginProperty.set(BotRequests.sendLoginRequest(email, password));
    }

    public void startBot(List<UserDefinedItem> userItems) {
        userItems.forEach(userItem -> {
            List<ItemPageResponse> responseItems = new ArrayList<>();
            if (!isCampaignLoaded(userItem.getCampaignID())) {
                responseItems = getCampaignItemsWhenCampaignAvailable(userItem);
            }
            List<ShopDefinedItem> parsedItems = getParsedItems(responseItems, userItem);
            List<ShopDefinedItemVariant> matchingItems = getMatchingItems(parsedItems, userItem);
            matchingItems.forEach(BotRequests::addItemToCart);
        });
    }


    private boolean isCampaignLoaded(String campaignID) {
        return campaignIDToItems.containsKey(campaignID);
    }

    private List<ShopDefinedItem> getParsedItems(List<ItemPageResponse> responseItems, UserDefinedItem seekedFor) {
        if (campaignIDToItems.get(seekedFor.getCampaignID()) == null) {
            campaignIDToItems.put(seekedFor.getCampaignID(), ResponseParser.parseItemPageResponses(responseItems));
        }
        return campaignIDToItems.get(seekedFor.getCampaignID());
    }

    private List<ItemPageResponse> getCampaignItemsWhenCampaignAvailable(UserDefinedItem userDefinedItem) {
        Logger.log("Pobieranie rzeczy z kampanii " + userDefinedItem.getCampaignIDForDisplay());
        List<ItemPageResponse> items = getItemsFromCampaign(userDefinedItem.getCampaignID());
        while (true) {
            if (items.isEmpty()) {
                return new ArrayList<>();
            } else {
                if (items.get(0).getPagination().getTotalArticles() == 0) {
                    try {
                        Logger.log("Kampania nie jest jeszcze aktywna, próbuję ponownie za " + SLEEP_BETWEEN_CAMPAIGN_LOAD_TRIRES + "ms.");
                        Thread.currentThread().sleep(SLEEP_BETWEEN_CAMPAIGN_LOAD_TRIRES);
                    } catch (Exception ex) {
                        if (ex instanceof InterruptedException) {
                            Thread.currentThread().stop();
                        } else {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    return items;
                }
            }

        }
    }

    private List<ShopDefinedItemVariant> getMatchingItems(List<ShopDefinedItem> allItems, UserDefinedItem userDefinedItem) {
        List<ShopDefinedItemVariant> matchingItemVariants = new ArrayList<>();
        allItems.stream()
                .forEach(item -> {
                    String fullName = item.getFullName().toLowerCase();
                    List<Boolean> containsKeyword = userDefinedItem.getKeywords().stream()
                            .map(keyword -> fullName.contains(keyword.toLowerCase()))
                            .collect(Collectors.toList());
                    if (!containsKeyword.contains(Boolean.FALSE)) {
                        Logger.log("Sprawdzanie czy jest rozmiar przedmiotu " + item.getFullName());
                        item.getVariantsAvailable().stream().forEach(itemVariant -> {
                            if (itemVariant.getSize().equals(userDefinedItem.getSize())) {
                                Logger.log("Znaleziono pasujący przedmiot: " + item.getFullName() + ", Rozmiar: " + itemVariant.getSize());
                                matchingItemVariants.add(itemVariant);
                            }
                        });
                    }
                });
        return matchingItemVariants;
    }

    public List<ItemPageResponse> getItemsFromCampaign(String campaignID) {
        List<ItemPageResponse> itemPageResponses = new ArrayList<>();
        ItemPageResponse unparsedItems = BotRequests.getItemsFromPage(campaignID, 0);
        if (unparsedItems == null) {
            return new ArrayList<>();
        }
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
