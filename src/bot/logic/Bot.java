package bot.logic;

import bot.data.ShopDefinedItem;
import bot.data.ShopDefinedItemVariant;
import bot.data.UserDefinedItem;
import bot.json.generated.itempage.ItemPageResponse;
import bot.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

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
        userItems.stream().forEach(userItem -> {
            Logger.log("Pobieranie rzeczy z kampanii " + userItem.getCampaignIDForDisplay());
            Optional<List<ItemPageResponse>> responseItems = getItemsFromCampaign(userItems.get(0).getCampaignID());
            if (responseItems.isPresent()) {
                List<ShopDefinedItem> parsedItems = ResponseParser.parseItemPageResponses(responseItems.get());
                List<ShopDefinedItemVariant> matchingItems = getMatchingItems(parsedItems, userItem);
                matchingItems.stream()
                        .forEach(matchingItem -> {
                            Logger.log("Dodawanie do koszyka " + matchingItem.getDisplayName() + " rozmiar " + matchingItem.getSize());
                            BotRequests.addItemToCart(matchingItem);
                        });

            } else {
                Logger.log("Oczekiwanie na rozpoczęcie kampanii " + userItem.getCampaignIDForDisplay());
            }
        });
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

    public Optional<List<ItemPageResponse>> getItemsFromCampaign(String campaignID) {
        List<ItemPageResponse> itemPageResponses = new ArrayList<>();
        ItemPageResponse unparsedItems = BotRequests.getItemsFromPage(campaignID, 0);
        if(unparsedItems == null){
            return Optional.empty();
        }
        itemPageResponses.add(unparsedItems);
        for (int i = 1; i < unparsedItems.getPagination().getTotalPages(); i++) {
            itemPageResponses.add(BotRequests.getItemsFromPage(campaignID, i));
        }
        return Optional.of(itemPageResponses);
    }

    public boolean isUserLogged() {
        return loginProperty.get();
    }

}
