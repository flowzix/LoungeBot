
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
    "EDITOR_CONFIG"
})
public class ZawartoOdpowiedzi {

    @JsonProperty("EDITOR_CONFIG")
    private EditorConfig eDITORCONFIG;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("EDITOR_CONFIG")
    public EditorConfig getEDITORCONFIG() {
        return eDITORCONFIG;
    }

    @JsonProperty("EDITOR_CONFIG")
    public void setEDITORCONFIG(EditorConfig eDITORCONFIG) {
        this.eDITORCONFIG = eDITORCONFIG;
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
