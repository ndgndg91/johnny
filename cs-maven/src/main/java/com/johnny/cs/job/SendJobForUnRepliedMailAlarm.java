package com.johnny.cs.job;

import com.johnny.cs.service.LineWorksService;
import com.johnny.cs.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 답장 보내지 않은 메일이 30분 지났을 때 알림 ( 야간, 휴일 )
 */
@Component
@RequiredArgsConstructor
public class SendJobForUnRepliedMailAlarm implements Job {

    private final SpreadSheetsService spreadSheetsService;

    private final LineWorksService lineWorksService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
