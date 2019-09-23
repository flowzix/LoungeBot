package bot.logic;

public class URLGenerator {
    private static final String LOGIN_POST_URL = "https://www.zalando-lounge.pl/onboarding-api/login";
    private static final String CAMPAIGN_API_BASE = "https://www.zalando-lounge.pl/api/campaigns/";
    private static final String ITEMS_FILTERS = "/articles?filter=%7B%7D&page=";
    public static String getLoginPostUrl(){
        return LOGIN_POST_URL;
    }
    public static String getCampaignItemsPage(String campaignID, int pageNo){
        return new StringBuilder()
                .append(CAMPAIGN_API_BASE)
                .append(campaignID)
                .append(ITEMS_FILTERS)
                .append(pageNo)
                .toString();
    }
}
