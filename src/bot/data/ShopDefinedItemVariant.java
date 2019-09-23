package bot.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShopDefinedItemVariant {
    private String sku;
    private Double price;
    private String size;
    private String campaignID;
    private String configSKU;
    private String displayName;
}
