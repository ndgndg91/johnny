package com.johnny.cs.line.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URLEncoder;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class AccessTokenRequest {
    private static final AccessTokenRequest request = new AccessTokenRequest();
    private static final String domain = "sixshop.com";

    @JsonProperty("client_id")
    private String clientId;
    private String code;

    private AccessTokenRequest(){}

    public static AccessTokenRequest getRequest(String clientId, String code){
        request.clientId = clientId;
        request.code = code;
        return request;
    }

    public String addQueryString(String accessTokenUrl){
        StringBuilder urlBuilder = new StringBuilder(accessTokenUrl); /*URL*/
        urlBuilder.append("?").append(URLEncoder.encode("client_id", UTF_8)).append("=").append(this.clientId);
        urlBuilder.append("&").append(URLEncoder.encode("code", UTF_8)).append("=").append(this.code);
        urlBuilder.append("&").append(URLEncoder.encode("domain", UTF_8)).append("=").append(domain); /*월*/
        return urlBuilder.toString();
    }
}
