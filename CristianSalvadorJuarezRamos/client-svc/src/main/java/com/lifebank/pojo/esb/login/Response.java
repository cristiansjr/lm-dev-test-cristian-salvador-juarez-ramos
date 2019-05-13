
package com.lifebank.pojo.esb.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "loginResponseCode"
})
public class Response {

    @JsonProperty("loginResponseCode")
    private Integer loginResponseCode;

    @JsonProperty("loginResponseCode")
    public Integer getLoginResponseCode() {
        return loginResponseCode;
    }

    @JsonProperty("loginResponseCode")
    public void setLoginResponseCode(Integer loginResponseCode) {
        this.loginResponseCode = loginResponseCode;
    }

}
