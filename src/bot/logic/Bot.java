package bot.logic;

import bot.constant.ItemFetchResult;
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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static bot.constant.BotConfig.SLEEP_BETWEEN_CAMPAIGN_LOAD_TRIRES;

public class Bot {
    @Getter
    private BooleanProperty loginProperty = new SimpleBooleanProperty(false);
    private Map<String, ConcurrentLinkedQueue<ItemPageResponse>> concurrentlyLoadedItems = new HashMap<>();
    private HashMap<String, List<UserDefinedItem>> itemsByCampaignNo = new HashMap<>();
    private volatile boolean isItemFetchingFinished = false;

    public Bot() {

    }

    public void loginUser(String email, String password) {
        loginProperty.set(BotRequests.sendLoginRequest(email, password));
    }

    private void addItemPagesToConcurrentQueue(List<String> campaignsToGet) {
        campaignsToGet.forEach(this::addCampaignItemsToQueueWhenAvailable);
    }

    private void processAvailableItems(List<UserDefinedItem> userItems) {
        while (true) {  // replace with when every campaign concurrent queue is empty ( processed )
            itemsByCampaignNo.keySet().stream().forEach(campaignNo -> {
                List<UserDefinedItem> itemsToGetInCampaign = itemsByCampaignNo.get(campaignNo);
                List<ItemPageResponse> responseItems = new ArrayList<>();
                extractConcurrentQueueToList(concurrentlyLoadedItems.get(campaignNo), responseItems);
                List<ShopDefinedItem> parsedItems = ResponseParser.parseItemPageResponses(responseItems);
                itemsToGetInCampaign.stream().forEach(userItem -> {
                    List<ShopDefinedItemVariant> matchingItems = getMatchingItems(parsedItems, userItem);
                    matchingItems.forEach(item -> BotRequests.addItemToCart(item));
                });
            });
            sleep(50);
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void extractConcurrentQueueToList(ConcurrentLinkedQueue queue, List list) {
        while (true) {
            Object resp = queue.poll();
            if (resp != null) {
                list.add(resp);
            } else {
                break;
            }
        }
    }

    public void startBot(List<UserDefinedItem> userItems) {
        Logger.log("Bot startuje...");

        List<String> campaignsToGet = getDistinctCampaignNosFromUserItems(userItems);
        Logger.log("Zostanie załadowane w sumie " + campaignsToGet.size() + " kampanii.");

        initializeCampaignMaps(campaignsToGet);
        userItems.forEach(item -> itemsByCampaignNo.get(item.getCampaignID()).add(item));

        Thread campaignFetchThread = new Thread(() -> addItemPagesToConcurrentQueue(campaignsToGet));
        campaignFetchThread.start();

        Thread itemProcessingThread = new Thread(() -> processAvailableItems(userItems));
        itemProcessingThread.start();
    }

    private List<String> getDistinctCampaignNosFromUserItems(List<UserDefinedItem> items) {
        return items.stream()
                .map(userItem -> userItem.getCampaignID())
                .distinct()
                .collect(Collectors.toList());
    }

    private void initializeCampaignMaps(List<String> mapNos) {
        mapNos.stream()
                .forEach(campaignNo -> {
                    concurrentlyLoadedItems.put(campaignNo, new ConcurrentLinkedQueue<>());
                    itemsByCampaignNo.put(campaignNo, new ArrayList<>());
                });

    }

    private void addCampaignItemsToQueueWhenAvailable(String campaignNo) {
        Logger.log("Pobieranie rzeczy z kampanii " + campaignNo);
        while (true) {
            ItemFetchResult result = addItemsToConcurrentQueue(campaignNo);
            switch (result) {
                case INVALID_CAMPAIGN:
                    Logger.log("Błędny numer kampanii " + campaignNo);
                    return;
                case NOT_YET_FETCHED:
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
                    return;
                case FETCHED:
                    Logger.log("Pobrano wszystkie rzeczy z kampanii " + campaignNo);
                    return;
            }
        }
    }

    private ItemFetchResult addItemsToConcurrentQueue(String campaignID) {
        ItemPageResponse unparsedItems = BotRequests.getItemsFromPage(campaignID, 0);
        if (unparsedItems == null) {
            return ItemFetchResult.INVALID_CAMPAIGN;
        }
        if (unparsedItems.getPagination().getTotalArticles() == 0) {
            return ItemFetchResult.NOT_YET_FETCHED;
        }
        concurrentlyLoadedItems.get(campaignID).offer(unparsedItems);
        for (int i = 1; i < unparsedItems.getPagination().getTotalPages(); i++) {
            concurrentlyLoadedItems.get(campaignID).offer(BotRequests.getItemsFromPage(campaignID, i));
        }
        return ItemFetchResult.FETCHED;
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
                            if (itemVariant.getSize().toLowerCase().equals(userDefinedItem.getSize().toLowerCase())) {
                                Logger.log("Znaleziono pasujący przedmiot: " + item.getFullName() + ", Rozmiar: " + itemVariant.getSize());
                                matchingItemVariants.add(itemVariant);
                            }
                        });
                    }
                });
        return matchingItemVariants;
    }

    public boolean isUserLogged() {
        return loginProperty.get();
    }

}
