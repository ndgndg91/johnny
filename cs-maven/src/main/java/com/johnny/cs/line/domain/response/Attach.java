package com.johnny.cs.line.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Attach {
    private int contentSN;
    private String filename;
    private int contentOffset;
    private long contentSize;
    private String contentType;
    private long decodedSize;
}
