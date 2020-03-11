package com.johnny.cs.line.domain.response.mailread;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class ReadMailResponse {
    private long currentTime;
    private MailInfo mailInfo;
}
