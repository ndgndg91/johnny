package com.johnny.cs.line.domain.response.mailread;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public final class ReadMailResponse {
    private long currentTime;
    private MailInfo mailInfo;
}
