package com.johnny.cs.core.job;

import com.johnny.cs.spreadsheet.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 야간 시작 5분 전 담당자에게 알림.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendJobToNighttimeCharger implements Job {

    private final SpreadSheetsService spreadSheetsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("오늘 평일 야간 담당자!!!!");
    }
}
