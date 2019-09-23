
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
    "JSON",
    "Zawarto\u015b\u0107 odpowiedzi"
})
public class Response {

    @JsonProperty("JSON")
    private ItemPageResponse jSON;
    @JsonProperty("Zawarto\u015b\u0107 odpowiedzi")
    private ZawartoOdpowiedzi zawartoOdpowiedzi;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("JSON")
    public ItemPageResponse getJSON() {
        return jSON;
    }

    @JsonProperty("JSON")
    public void setJSON(ItemPageResponse jSON) {
        this.jSON = jSON;
    }

    @JsonProperty("Zawarto\u015b\u0107 odpowiedzi")
    public ZawartoOdpowiedzi getZawartoOdpowiedzi() {
        return zawartoOdpowiedzi;
    }

    @JsonProperty("Zawarto\u015b\u0107 odpowiedzi")
    public void setZawartoOdpowiedzi(ZawartoOdpowiedzi zawartoOdpowiedzi) {
        this.zawartoOdpowiedzi = zawartoOdpowiedzi;
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
