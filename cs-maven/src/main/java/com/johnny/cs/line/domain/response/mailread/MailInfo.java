package com.johnny.cs.line.domain.response.mailread;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.johnny.cs.line.domain.response.common.From;
import com.johnny.cs.line.domain.response.common.To;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
@ToString
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

    public String whatIsStatus(){
        if (0x4L == (0x4L & status)) {
            log.info("{}", status);
            log.info("{}", 0x4L & status);
            return "읽은 메일(현재 상태)";
        }

        if (0x8L == (0x8L & status)) {
            return "답장을 보냄";
        }

        if (0x10L == (0x10L & status)) {
            return "전달함";
        }

        if (0x20L == (0x20L & status)) {
            return "중요 메일로 표시함";
        }

        if (0x100L == (0x100L & status)) {
            return "보낸 메일임";
        }

        if (0x800L == (0x800L & status)) {
            return "상대방이 읽음";
        }

        if (0x1000L == (0x1000L & status)) {
            return "대용량 첨부 메일";
        }

        if (0x40000L == (0x40000L & status)) {
            return "답장을 받은 메일";
        }

        if (0x00200000L == (0x00200000L & status)) {
            return "내가 수신자로 설정되어 있는 메일";
        }

        if (0x00400000L == (0x00400000L & status)) {
            return "일정 메일";
        }

        return "알 수 없음";
    }
}