package bot.logic;

import bot.data.ShopDefinedItem;
import bot.data.ShopDefinedItemVariant;
import bot.json.generated.itempage.ItemPageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseParser {
    public static List<ShopDefinedItem> parseItemPageResponses(List<ItemPageResponse> responses) {
        List<ShopDefinedItem> parsedItems = new ArrayList<>();
        responses.stream()
                .forEach(response -> response.getResults().stream()
                        .forEach(result -> {
                            List<ShopDefinedItemVariant> variants = result.getSimples().stream()
                                    .map(s -> ShopDefinedItemVariant.builder()
                                            .price(s.getSpecialPrice() / 100.0)
                                            .sku(s.getSku())
                                            .build())
                                    .collect(Collectors.toList());

                            parsedItems.add(ShopDefinedItem.builder()
                                    .brand(result.getBrand())
                                    .campaignID(result.getCampaignIdentifier())
                                    .name(result.getNameCategoryTag())
                                    .color(result.getNameColor())
                                    .itemSKU(result.getSku())
                                    .url(result.getUrlPath().toString())
                                    .variantsAvailable(variants)
                                    .build());
                        }));
        return parsedItems;
    }
}
