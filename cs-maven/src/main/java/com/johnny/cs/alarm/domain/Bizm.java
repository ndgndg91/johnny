package com.johnny.cs.alarm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Bizm {
    private String sendType;
    private String msgId;
    private String profileKey;
    private String sendPhone;
    private String receiverNum;
    private String templateCode;
    private String sendContent;
}
