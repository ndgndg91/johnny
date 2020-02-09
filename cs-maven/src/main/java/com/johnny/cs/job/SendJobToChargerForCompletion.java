package com.johnny.cs.job;

import com.johnny.cs.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * CS 일정이 끝난 뒤 수고 인사 ( 야간, 휴일 )
 */
@Component
@RequiredArgsConstructor
public class SendJobToChargerForCompletion implements Job {

    private final SpreadSheetsService spreadSheetsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
