
package koreatech.cse.domain.oauth2.kakao.book;

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
    "is_end",
    "pageable_count",
    "total_count"
})
public class Meta {

    @JsonProperty("is_end")
    private Boolean isEnd;
    @JsonProperty("pageable_count")
    private Integer pageableCount;
    @JsonProperty("total_count")
    private Integer totalCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("is_end")
    public Boolean getIsEnd() {
        return isEnd;
    }

    @JsonProperty("is_end")
    public void setIsEnd(Boolean isEnd) {
        this.isEnd = isEnd;
    }

    @JsonProperty("pageable_count")
    public Integer getPageableCount() {
        return pageableCount;
    }

    @JsonProperty("pageable_count")
    public void setPageableCount(Integer pageableCount) {
        this.pageableCount = pageableCount;
    }

    @JsonProperty("total_count")
    public Integer getTotalCount() {
        return totalCount;
    }

    @JsonProperty("total_count")
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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
