package bot.logic;

import bot.data.ShopDefinedItemVariant;
import bot.json.generated.cart.Additional;
import bot.json.generated.cart.Cart;
import bot.json.generated.cart.JSON;
import bot.json.generated.itempage.ItemPageResponse;
import bot.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import org.apache.http.HttpResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BotRequests {

    public static boolean sendLoginRequest(String email, String password) {
        try {
            return RequestSender.postJSON(URLProvider.getLoginPostUrl(), LoungeJSONBuilder.getLoginJSON(email, password));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ItemPageResponse getItemsFromPage(String campaignID, int pageNo) {
        try {
            HttpResponse response = RequestSender.get(URLProvider.getCampaignItemsPage(campaignID, pageNo), getItemPageHeaders());
            ObjectMapper om = new ObjectMapper();
            return om.readValue(response.getEntity().getContent(), ItemPageResponse.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void addItemToCart(ShopDefinedItemVariant item) {
        Logger.log("Dodawanie do koszyka " + item.getDisplayName() + " rozmiar " + item.getSize());
        try {
            Additional additional = new Additional();
            additional.setReco(0);
            JSON jsonInner = JSON.builder()
                    .additional(additional)
                    .campaignIdentifier(item.getCampaignID())
                    .configSku(item.getConfigSKU())
                    .simpleSku(item.getSku())
                    .ignoreExceptionCodes(Arrays.asList(506))
                    .build();
            RequestSender.putJSON(URLProvider.getCartPutUrl(), jsonInner, getCartPutHeaders());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static List<Pair<String, String>> getItemPageHeaders() {
        List<Pair<String, String>> headers = new ArrayList<>();
        headers.add(new Pair("Accept", "application/json, text/plain, */*"));
        headers.add(new Pair("Accept-Encoding", "gzip, deflate, br"));
        headers.add(new Pair("Host", "www.zalando-lounge.pl"));
        headers.add(new Pair("Accept-Language", "pl,en-US;q=0.7,en;q=0.3"));
        headers.add(new Pair("X-Requested-With", "XMLHttpRequest"));
        return headers;
    }

    private static List<Pair<String, String>> getCartPutHeaders() {
        List<Pair<String, String>> headers = new ArrayList<>();
        headers.add(new Pair("Accept", "application/json, text/plain, */*"));
        headers.add(new Pair("Accept-Encoding", "gzip, deflate, br"));
        headers.add(new Pair("Host", "www.zalando-lounge.pl"));
        headers.add(new Pair("Accept-Language", "pl,en-US;q=0.7,en;q=0.3"));
        headers.add(new Pair("X-Requested-With", "XMLHttpRequest"));
        return headers;
    }
}
