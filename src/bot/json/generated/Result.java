
package bot.json.generated;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sku",
        "brand",
        "brandCode",
        "categoryId",
        "images",
        "media",
        "subtitle",
        "nameShop",
        "nameCategoryTag",
        "nameColor",
        "urlPath",
        "silhouette",
        "simples",
        "hasSimilar",
        "campaignIdentifier",
        "savings",
        "gender",
        "stockStatus",
        "attributes"
})
public class Result {

    @JsonProperty("sku")
    private String sku;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("brandCode")
    private String brandCode;
    @JsonProperty("categoryId")
    private Integer categoryId;
    @JsonProperty("images")
    private List<String> images = null;
    @JsonProperty("media")
    private List<Medium> media = null;
    @JsonProperty("subtitle")
    private String subtitle;
    @JsonProperty("nameShop")
    private String nameShop;
    @JsonProperty("nameCategoryTag")
    private String nameCategoryTag;
    @JsonProperty("nameColor")
    private String nameColor;
    @JsonProperty("urlPath")
    private UrlPath urlPath;
    @JsonProperty("silhouette")
    private String silhouette;
    @JsonProperty("simples")
    private List<Simple> simples = null;
    @JsonProperty("hasSimilar")
    private Boolean hasSimilar;
    @JsonProperty("campaignIdentifier")
    private String campaignIdentifier;
    @JsonProperty("savings")
    private Integer savings;
    @JsonProperty("gender")
    private List<String> gender = null;
    @JsonProperty("stockStatus")
    private String stockStatus;
    @JsonProperty("attributes")
    private Attributes attributes;
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

    @JsonProperty("brand")
    public String getBrand() {
        return brand;
    }

    @JsonProperty("brand")
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @JsonProperty("brandCode")
    public String getBrandCode() {
        return brandCode;
    }

    @JsonProperty("brandCode")
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    @JsonProperty("categoryId")
    public Integer getCategoryId() {
        return categoryId;
    }

    @JsonProperty("categoryId")
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @JsonProperty("images")
    public List<String> getImages() {
        return images;
    }

    @JsonProperty("images")
    public void setImages(List<String> images) {
        this.images = images;
    }

    @JsonProperty("media")
    public List<Medium> getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    @JsonProperty("subtitle")
    public String getSubtitle() {
        return subtitle;
    }

    @JsonProperty("subtitle")
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @JsonProperty("nameShop")
    public String getNameShop() {
        return nameShop;
    }

    @JsonProperty("nameShop")
    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    @JsonProperty("nameCategoryTag")
    public String getNameCategoryTag() {
        return nameCategoryTag;
    }

    @JsonProperty("nameCategoryTag")
    public void setNameCategoryTag(String nameCategoryTag) {
        this.nameCategoryTag = nameCategoryTag;
    }

    @JsonProperty("nameColor")
    public String getNameColor() {
        return nameColor;
    }

    @JsonProperty("nameColor")
    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    @JsonProperty("urlPath")
    public UrlPath getUrlPath() {
        return urlPath;
    }

    @JsonProperty("urlPath")
    public void setUrlPath(UrlPath urlPath) {
        this.urlPath = urlPath;
    }

    @JsonProperty("silhouette")
    public String getSilhouette() {
        return silhouette;
    }

    @JsonProperty("silhouette")
    public void setSilhouette(String silhouette) {
        this.silhouette = silhouette;
    }

    @JsonProperty("simples")
    public List<Simple> getSimples() {
        return simples;
    }

    @JsonProperty("simples")
    public void setSimples(List<Simple> simples) {
        this.simples = simples;
    }

    @JsonProperty("hasSimilar")
    public Boolean getHasSimilar() {
        return hasSimilar;
    }

    @JsonProperty("hasSimilar")
    public void setHasSimilar(Boolean hasSimilar) {
        this.hasSimilar = hasSimilar;
    }

    @JsonProperty("campaignIdentifier")
    public String getCampaignIdentifier() {
        return campaignIdentifier;
    }

    @JsonProperty("campaignIdentifier")
    public void setCampaignIdentifier(String campaignIdentifier) {
        this.campaignIdentifier = campaignIdentifier;
    }

    @JsonProperty("savings")
    public Integer getSavings() {
        return savings;
    }

    @JsonProperty("savings")
    public void setSavings(Integer savings) {
        this.savings = savings;
    }

    @JsonProperty("gender")
    public List<String> getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    @JsonProperty("stockStatus")
    public String getStockStatus() {
        return stockStatus;
    }

    @JsonProperty("stockStatus")
    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    @JsonProperty("attributes")
    public Attributes getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
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
