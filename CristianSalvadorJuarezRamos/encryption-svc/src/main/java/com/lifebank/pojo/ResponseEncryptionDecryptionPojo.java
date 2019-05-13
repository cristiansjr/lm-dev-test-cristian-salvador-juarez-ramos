package com.lifebank.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "outputString"
})
public class ResponseEncryptionDecryptionPojo {

    @JsonProperty("outputString")
    private String outputString;

    @JsonProperty("outputString")
    public String getOutputString() {
        return outputString;
    }

    @JsonProperty("outputString")
    public void setOutputString(String outputString) {
        this.outputString = outputString;
    }

}
