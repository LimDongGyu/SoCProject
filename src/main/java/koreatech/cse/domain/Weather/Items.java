
package koreatech.cse.domain.weather;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "item"
})
public class Items {

    @JsonProperty("item")
    private List<Item> item = null;

    @JsonProperty("item")
    public List<Item> getItem() {
        return item;
    }

    @JsonProperty("item")
    public void setItem(List<Item> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Items{" +
                "item=" + item +
                '}';
    }
}
