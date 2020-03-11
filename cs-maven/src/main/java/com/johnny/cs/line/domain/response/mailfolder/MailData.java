package com.johnny.cs.line.domain.response.mailfolder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public final class MailData {
    private long mailSN;
    private int folderSN;
    private String folderName;
    private int iconType;
    private int status;
    private From from;
    private String subject;
    private long receivedTime;
    private long sentTime;
    private long size;
    private int attachCount;
    private String iDomainEmail;
    private int priority;
    private String securityLevel;
    private boolean reSend;
    private String color;
    private String preview;
    private String mimeSN;
    private List<To> toList;
    private String spamType;
    private List<Attach> attachInfo;
    private String threadId;
    private boolean receiveByBcc;
    private int threadCount;
    private int unreadThreadCount;
    private long firstLocatedTime;
    private int remindAt;
    private int threadRemindAt;
    private int threadRemindFlag;
    private int category;
    private long lastLocatedTime;
    private boolean toMe;
    private boolean bigFile;
    private String statusHex;
    private boolean isDomainMail;
}
