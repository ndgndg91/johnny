package com.johnny.cs.line.domain.response.mailread;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.johnny.cs.line.domain.response.common.From;
import com.johnny.cs.line.domain.response.common.To;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class MailInfo {
    private long mailSN;
    private int folderSN;
    private long status;
    private String mimeSN;
    private From from;
    private List<To> toList;
    private String subject;
    private String body;
    private long receivedTime; //메일 수신 시간
    private long sentTime; //메일 발송 시간
    private int statusTime; //전달/답장한 시간
    private int size; //메일 크기
    private int attachCount; //첨부 파일 개수
    private int textBodyContentSN; //텍스트 메일 본문 일련번호
    private int htmlBodyContentSN; //HTML 메일 본문 일련번호
    private int priority; //  메일의 중요도
    private String spamTypeDetail; //스팸 상세 설명
    private String securityLevel; //메일의 보안 수준.
    private boolean reSend; // 메일 재전송 가능 여부.

    public boolean hasRead(){
        return 0x4L == (0x4L & status);
    }

    public boolean hasSendReplyMail(){
        return 0x8L == (0x8L & status);
    }

    public boolean hasNotSendReplyMail(){
        return !hasSendReplyMail();
    }

    public boolean hasRelayed(){
        return 0x10L == (0x10L & status);
    }

    public boolean hasImportantMark(){
        return 0x20L == (0x20L & status);
    }

    public boolean hasSend(){
        return 0x100L == (0x100L & status);
    }

    public boolean hasReadByOtherSide(){
        return 0x800L == (0x800L & status);
    }

    public boolean hasLargeAttachment(){
        return 0x1000L == (0x1000L & status);
    }

    public boolean hasReceivedReplyMail(){
        return 0x40000L == (0x40000L & status);
    }

    public boolean haveBeenSetAsARecipient(){
        return 0x00200000L == (0x00200000L & status);
    }

    public boolean hasScheduledMail(){
        return 0x00400000L == (0x00400000L & status);
    }


    /**
     * 답장 안 한지 30분이 지났는지
     */
    public boolean isUnRepliedForThirtyMinutes(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime mailReceivedTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(receivedTime), ZoneId.systemDefault());
        return ChronoUnit.MINUTES.between(mailReceivedTime, now) >= 30;
    }

    /**
     * 답장 안 한지 1시간이 지났는지
     */
    public boolean isUnRepliedForOneHour(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime mailReceivedTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(receivedTime), ZoneId.systemDefault());
        return ChronoUnit.HOURS.between(mailReceivedTime, now) >= 1;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("mailSN", mailSN)
                .append("from", from)
                .append("toList", toList)
                .append("subject", subject)
                .append("attachCount", attachCount)
                .toString();
    }
}

