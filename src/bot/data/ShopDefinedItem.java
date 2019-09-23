package bot.data;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDefinedItem {
    private String campaignID;
    private String brand;
    private String name;
    private String color;
    private List<ShopDefinedItemVariant> variantsAvailable;
    private String itemSKU;
    private String url;
    public String getFullName() {
        return name + " " + color;
    }
}
