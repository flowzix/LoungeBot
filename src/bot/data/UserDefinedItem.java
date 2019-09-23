package bot.data;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

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
        return keywords.stream().map(keyword -> keyword + ";").collect(Collectors.joining());
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
