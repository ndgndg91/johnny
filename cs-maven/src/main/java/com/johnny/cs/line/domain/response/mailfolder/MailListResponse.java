package com.johnny.cs.line.domain.response.mailfolder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public final class MailListResponse {
    private long currentTime;
    private int lastPage;
    private int pageSize;
    private int unreadCount;
    private List<MailData> mailData;
    private String folderName;
    private int totalCount;
    private int listCount;
}
