
package com.lifebank.pojo.service.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tkn"
})
public class ResponseLoginPojo {

    @JsonProperty("tkn")
    private String tkn;

    @JsonProperty("tkn")
    public String getTkn() {
        return tkn;
    }

    @JsonProperty("tkn")
    public void setTkn(String tkn) {
        this.tkn = tkn;
    }

}
