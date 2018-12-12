
package koreatech.cse.domain.oauth2.kakao;

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
    "has_email",
    "has_age_range",
    "has_birthday",
    "has_gender"
})
public class KakaoAccount {

    @JsonProperty("has_email")
    private Boolean hasEmail;
    @JsonProperty("has_age_range")
    private Boolean hasAgeRange;
    @JsonProperty("has_birthday")
    private Boolean hasBirthday;
    @JsonProperty("has_gender")
    private Boolean hasGender;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("has_email")
    public Boolean getHasEmail() {
        return hasEmail;
    }

    @JsonProperty("has_email")
    public void setHasEmail(Boolean hasEmail) {
        this.hasEmail = hasEmail;
    }

    @JsonProperty("has_age_range")
    public Boolean getHasAgeRange() {
        return hasAgeRange;
    }

    @JsonProperty("has_age_range")
    public void setHasAgeRange(Boolean hasAgeRange) {
        this.hasAgeRange = hasAgeRange;
    }

    @JsonProperty("has_birthday")
    public Boolean getHasBirthday() {
        return hasBirthday;
    }

    @JsonProperty("has_birthday")
    public void setHasBirthday(Boolean hasBirthday) {
        this.hasBirthday = hasBirthday;
    }

    @JsonProperty("has_gender")
    public Boolean getHasGender() {
        return hasGender;
    }

    @JsonProperty("has_gender")
    public void setHasGender(Boolean hasGender) {
        this.hasGender = hasGender;
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
