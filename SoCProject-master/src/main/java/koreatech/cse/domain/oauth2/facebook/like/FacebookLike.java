package koreatech.cse.domain.oauth2.facebook.like;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "data",
        "paging"
})
public class FacebookLike {

    @JsonProperty("data")
    private List<Datum> data = new ArrayList<Datum>();
    @JsonProperty("paging")
    private Paging paging;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    @Override
    public String toString() {
        return "FacebookLike{" +
                "data=" + data +
                ", paging=" + paging +
                '}';
    }
}