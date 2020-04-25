package com.johnny.cs.alarm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Bizm {
    @JsonIgnore
    private String name;
    @JsonIgnore
    private String sendPhone;

    @JsonProperty("msgid")
    private String msgId;

    @JsonProperty("profile_key")
    private String profileKey;

    @JsonProperty("receiver_num")
    private String receiverNum;

    @JsonProperty("template_code")
    private String templateCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("reserved_time")
    private String reservedTime;

    public void reviseChargerName(){
        name = name.replace("(휴)", "");
        message = message.replace("#{CS_NAME}", name);
    }
}
