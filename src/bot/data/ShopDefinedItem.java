package bot.data;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDefinedItem {
    private String brand;
    private String name;
    private String color;
    private List<ShopDefinedItemVariant> variantsAvailable;

    public String getFullName() {
        return brand + " " + name + " " + color;
    }
}
