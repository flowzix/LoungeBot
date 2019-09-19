package bot;
import org.json.JSONObject;
public class LoungeJSONBuilder {
    public static String getLoginJSON(String email, String password){
        return new JSONObject()
                .put("email", email)
                .put("password", password)
                .toString();
    }
}
