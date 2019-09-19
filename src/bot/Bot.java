package bot;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

public class Bot {
    @Getter
    private BooleanProperty loginProperty = new SimpleBooleanProperty(false);

    public Bot() {

    }

    public void loginUser(String email, String password) {
        try {
            boolean result = RequestSender.postJSON(BotConfig.LOGIN_POST_URL,
                    LoungeJSONBuilder.getLoginJSON(email, password));
            loginProperty.set(result);
        } catch (Exception e) {
            // TODO: Status - CONNECTION/URI ERRORS
            loginProperty.set(false);
            e.printStackTrace();
        }
        // TODO: MAKE A WRAPPER FOR THIS FUNCTION, SEEMS LIKE LOGIC MIXING
    }

}
