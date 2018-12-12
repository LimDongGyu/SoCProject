
package koreatech.cse.domain.weather;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "items",
    "numOfRows",
    "pageNo",
    "totalCount"
})
public class Body {

    @JsonProperty("items")
    private Items items;
    @JsonProperty("numOfRows")
    private Integer numOfRows;
    @JsonProperty("pageNo")
    private Integer pageNo;
    @JsonProperty("totalCount")
    private Integer totalCount;

    @JsonProperty("items")
    public Items getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(Items items) {
        this.items = items;
    }

    @JsonProperty("numOfRows")
    public Integer getNumOfRows() {
        return numOfRows;
    }

    @JsonProperty("numOfRows")
    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    @JsonProperty("pageNo")
    public Integer getPageNo() {
        return pageNo;
    }

    @JsonProperty("pageNo")
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    @JsonProperty("totalCount")
    public Integer getTotalCount() {
        return totalCount;
    }

    @JsonProperty("totalCount")
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "Body{" +
                "items=" + items +
                ", numOfRows=" + numOfRows +
                ", pageNo=" + pageNo +
                ", totalCount=" + totalCount +
                '}';
    }
}
