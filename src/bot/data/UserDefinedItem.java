package bot.data;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserDefinedItem {
    private List<String> keywords;
    private String size;
    private boolean chooseAnyIfNotAvailable;
    private Double maxPrice;
    private String campaignID;

    public String getKeywordsForDisplay() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < keywords.size() - 1; i++) {
            stringBuilder.append(keywords.get(i));
            stringBuilder.append(";");
        }
        stringBuilder.append(keywords.get(keywords.size() - 1));
        return stringBuilder.toString();
        //TODO: use stringUtils from apache to join it
    }

    public String getSizeForDisplay() {
        return size;
    }

    public String getAnySizeForDisplay() {
        return chooseAnyIfNotAvailable ? "Tak" : "Nie";
    }

    public String getMaxPriceForDisplay() {
        return maxPrice.toString();
    }

    public String getCampaignIDForDisplay() {
        return campaignID;
    }
}
