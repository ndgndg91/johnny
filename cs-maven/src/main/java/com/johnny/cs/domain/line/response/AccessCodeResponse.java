package com.johnny.cs.domain.line.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public final class AccessCodeResponse {
    private String errorCode;
    private String accessToken;
    private String refreshToken;
    private String expireIn;
}
