package bot.logic;

public class URLProvider {
    private static final String LOGIN_POST_URL = "https://www.zalando-lounge.pl/onboarding-api/login";
    private static final String CAMPAIGN_API_BASE = "https://www.zalando-lounge.pl/api/campaigns/";
    private static final String ITEMS_FILTERS = "/articles?filter=%7B%7D&page=";
    private static final String CART_PUT_URL = "https://www.zalando-lounge.pl/api/checkout/cart/items";

    public static String getLoginPostUrl() {
        return LOGIN_POST_URL;
    }

    public static String getCampaignItemsPage(String campaignID, int pageNo) {
        return new StringBuilder()
                .append(CAMPAIGN_API_BASE)
                .append(campaignID)
                .append(ITEMS_FILTERS)
                .append(pageNo)
                .toString();
    }

    public static String getCartPutUrl() {
        return CART_PUT_URL;
    }
}
