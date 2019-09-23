
package bot.json.generated.itempage;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sku",
    "price",
    "specialPrice",
    "filterName",
    "filterValue",
    "stockStatus",
    "stockHasReservations",
    "country_sizes"
})
public class Simple {

    @JsonProperty("sku")
    private String sku;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("specialPrice")
    private Integer specialPrice;
    @JsonProperty("filterName")
    private String filterName;
    @JsonProperty("filterValue")
    private String filterValue;
    @JsonProperty("stockStatus")
    private String stockStatus;
    @JsonProperty("stockHasReservations")
    private Boolean stockHasReservations;
    @JsonProperty("country_sizes")
    private CountrySizes countrySizes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sku")
    public String getSku() {
        return sku;
    }

    @JsonProperty("sku")
    public void setSku(String sku) {
        this.sku = sku;
    }

    @JsonProperty("price")
    public Integer getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Integer price) {
        this.price = price;
    }

    @JsonProperty("specialPrice")
    public Integer getSpecialPrice() {
        return specialPrice;
    }

    @JsonProperty("specialPrice")
    public void setSpecialPrice(Integer specialPrice) {
        this.specialPrice = specialPrice;
    }

    @JsonProperty("filterName")
    public String getFilterName() {
        return filterName;
    }

    @JsonProperty("filterName")
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    @JsonProperty("filterValue")
    public String getFilterValue() {
        return filterValue;
    }

    @JsonProperty("filterValue")
    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    @JsonProperty("stockStatus")
    public String getStockStatus() {
        return stockStatus;
    }

    @JsonProperty("stockStatus")
    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    @JsonProperty("stockHasReservations")
    public Boolean getStockHasReservations() {
        return stockHasReservations;
    }

    @JsonProperty("stockHasReservations")
    public void setStockHasReservations(Boolean stockHasReservations) {
        this.stockHasReservations = stockHasReservations;
    }

    @JsonProperty("country_sizes")
    public CountrySizes getCountrySizes() {
        return countrySizes;
    }

    @JsonProperty("country_sizes")
    public void setCountrySizes(CountrySizes countrySizes) {
        this.countrySizes = countrySizes;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
