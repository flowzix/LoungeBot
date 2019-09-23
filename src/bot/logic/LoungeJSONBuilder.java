package bot.logic;

import org.json.JSONObject;

public class LoungeJSONBuilder {
    // simple request, not created by object
    public static String getLoginJSON(String email, String password) {
        return new JSONObject()
                .put("email", email)
                .put("password", password)
                .toString();
    }


}
