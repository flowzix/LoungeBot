package bot;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopItem {
    @Setter
    private List<String> keywords;
    @Setter
    private String size;
    @Setter
    private boolean chooseAnyIfNotAvailable;
    @Setter
    private Double maxPrice;

    public String getKeywordsForDisplay() {
        return keywords.stream().map(keyword -> keyword + ";").collect(Collectors.joining());
    }

    public String getSizeForDisplay() {
        return size;
    }

    public String getAnySizeForDisplay() {
        return Boolean.toString(chooseAnyIfNotAvailable);
    }

    public String getMaxPriceForDisplay() {
        return maxPrice.toString();
    }
}
