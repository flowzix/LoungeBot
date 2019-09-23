
package bot.json.generated.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "additional",
    "campaignIdentifier",
    "configSku",
    "ignoreExceptionCodes",
    "simpleSku"
})
@AllArgsConstructor
@Builder
public class JSON {

    @JsonProperty("additional")
    private Additional additional;
    @JsonProperty("campaignIdentifier")
    private String campaignIdentifier;
    @JsonProperty("configSku")
    private String configSku;
    @JsonProperty("ignoreExceptionCodes")
    private List<Integer> ignoreExceptionCodes = null;
    @JsonProperty("simpleSku")
    private String simpleSku;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("additional")
    public Additional getAdditional() {
        return additional;
    }

    @JsonProperty("additional")
    public void setAdditional(Additional additional) {
        this.additional = additional;
    }

    @JsonProperty("campaignIdentifier")
    public String getCampaignIdentifier() {
        return campaignIdentifier;
    }

    @JsonProperty("campaignIdentifier")
    public void setCampaignIdentifier(String campaignIdentifier) {
        this.campaignIdentifier = campaignIdentifier;
    }

    @JsonProperty("configSku")
    public String getConfigSku() {
        return configSku;
    }

    @JsonProperty("configSku")
    public void setConfigSku(String configSku) {
        this.configSku = configSku;
    }

    @JsonProperty("ignoreExceptionCodes")
    public List<Integer> getIgnoreExceptionCodes() {
        return ignoreExceptionCodes;
    }

    @JsonProperty("ignoreExceptionCodes")
    public void setIgnoreExceptionCodes(List<Integer> ignoreExceptionCodes) {
        this.ignoreExceptionCodes = ignoreExceptionCodes;
    }

    @JsonProperty("simpleSku")
    public String getSimpleSku() {
        return simpleSku;
    }

    @JsonProperty("simpleSku")
    public void setSimpleSku(String simpleSku) {
        this.simpleSku = simpleSku;
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
