package com.lifebank.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "inputString"
})
public class RequestEncryptionDecryptionPojo {

    @JsonProperty("inputString")
    private String inputString;

    @JsonProperty("inputString")
    public String getInputString() {
        return inputString;
    }

    @JsonProperty("inputString")
    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

}
