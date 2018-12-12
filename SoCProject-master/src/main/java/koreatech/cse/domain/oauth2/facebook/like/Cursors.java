package koreatech.cse.domain.oauth2.facebook.like;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "before",
        "after"
})
public class Cursors {

    @JsonProperty("before")
    private String before;
    @JsonProperty("after")
    private String after;

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    @Override
    public String toString() {
        return "Cursors{" +
                "before='" + before + '\'' +
                ", after='" + after + '\'' +
                '}';
    }
}