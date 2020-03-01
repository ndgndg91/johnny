package com.johnny.cs.line.domain.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public final class AuthorizationCodeRequest {
    private String code;
    private String state;
    private String errorCode;
}
