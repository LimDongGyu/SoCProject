
package koreatech.cse.domain.weather;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "baseDate",
    "baseTime",
    "category",
    "nx",
    "ny",
    "obsrValue"
})
public class Item {

    @JsonProperty("baseDate")
    private Integer baseDate;
    @JsonProperty("baseTime")
    private Integer baseTime;
    @JsonProperty("category")
    private String category;
    @JsonProperty("nx")
    private Integer nx;
    @JsonProperty("ny")
    private Integer ny;
    @JsonProperty("obsrValue")
    private Integer obsrValue;

    @JsonProperty("baseDate")
    public Integer getBaseDate() {
        return baseDate;
    }

    @JsonProperty("baseDate")
    public void setBaseDate(Integer baseDate) {
        this.baseDate = baseDate;
    }

    @JsonProperty("baseTime")
    public Integer getBaseTime() {
        return baseTime;
    }

    @JsonProperty("baseTime")
    public void setBaseTime(Integer baseTime) {
        this.baseTime = baseTime;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("nx")
    public Integer getNx() {
        return nx;
    }

    @JsonProperty("nx")
    public void setNx(Integer nx) {
        this.nx = nx;
    }

    @JsonProperty("ny")
    public Integer getNy() {
        return ny;
    }

    @JsonProperty("ny")
    public void setNy(Integer ny) {
        this.ny = ny;
    }

    @JsonProperty("obsrValue")
    public Integer getObsrValue() {
        return obsrValue;
    }

    @JsonProperty("obsrValue")
    public void setObsrValue(Integer obsrValue) {
        this.obsrValue = obsrValue;
    }

    @Override
    public String toString() {
        return "Item{" +
                "baseDate=" + baseDate +
                ", baseTime=" + baseTime +
                ", category='" + category + '\'' +
                ", nx=" + nx +
                ", ny=" + ny +
                ", obsrValue=" + obsrValue +
                '}';
    }
}
