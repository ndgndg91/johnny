package com.johnny.cs.domain.line.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class AccessCodeRequest {
    private static final AccessCodeRequest request = new AccessCodeRequest();
    @JsonProperty("client_id")
    private String clientId;
    private String code;
    private String domain = "sixshop.com";

    private AccessCodeRequest(){}

    public static AccessCodeRequest getRequest(String clientId, String code){
        request.clientId = clientId;
        request.code = code;
        return request;
    }
}
