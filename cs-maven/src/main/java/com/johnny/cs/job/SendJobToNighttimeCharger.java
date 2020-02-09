package com.johnny.cs.job;

import com.johnny.cs.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 야간 시작 5분 전 담당자에게 알림.
 */
@Component
@RequiredArgsConstructor
public class SendJobToNighttimeCharger implements Job {

    private final SpreadSheetsService spreadSheetsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
