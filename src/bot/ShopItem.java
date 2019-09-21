package bot;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ShopItem {
    List<String> keywords;
    String size;
    boolean chooseAnyIfNotAvailable;
    Double maxPrice;
}
