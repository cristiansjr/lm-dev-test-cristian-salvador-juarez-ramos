
package com.lifebank.pojo.esb.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "_postvalidate-credential"
})
public class RequestESBLoginPojo {

    @JsonProperty("_postvalidate-credential")
    private PostvalidateCredential postvalidateCredential;

    @JsonProperty("_postvalidate-credential")
    public PostvalidateCredential getPostvalidateCredential() {
        return postvalidateCredential;
    }

    @JsonProperty("_postvalidate-credential")
    public void setPostvalidateCredential(PostvalidateCredential postvalidateCredential) {
        this.postvalidateCredential = postvalidateCredential;
    }

}
