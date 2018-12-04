package koreatech.cse.domain.oauth2.facebook.like;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import koreatech.cse.domain.oauth2.facebook.like.Cursors;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "cursors",
        "next"
})
public class Paging {
    @JsonProperty("cursors")
    private Cursors cursors;
    @JsonProperty("next")
    private String next;

    public Cursors getCursors() {
        return cursors;
    }

    public void setCursors(Cursors cursors) {
        this.cursors = cursors;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "cursors=" + cursors +
                ", next='" + next + '\'' +
                '}';
    }
}