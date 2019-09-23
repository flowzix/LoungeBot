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
}
