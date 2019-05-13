package com.lifebank.pojo.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "errorString"
})
public class ResponseError {
	@JsonProperty("errorString")
	private String errorString;

	@JsonProperty("errorString")
	public String getErrorString() {
		return errorString;
	}

	@JsonProperty("errorString")
	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}
	
	
}
