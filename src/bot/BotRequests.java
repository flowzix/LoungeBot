package bot;

import bot.json.generated.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Pair;
import org.apache.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class BotRequests {

    public static boolean sendLoginRequest(String email, String password) {
        try {
            return RequestSender.postJSON(BotConfig.LOGIN_POST_URL, LoungeJSONBuilder.getLoginJSON(email, password));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JSON getItemPageRequest(String url) {
        try {
            HttpResponse response = RequestSender.get(url, getItemPageHeaders());
            ObjectMapper om = new ObjectMapper();
            return om.readValue(response.getEntity().getContent(), JSON.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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
}
