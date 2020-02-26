package com.johnny.cs.job;

import com.johnny.cs.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 내일 오전, 오후, 휴일 CS 담당자에게 알림. ( 오전, 오후, 휴일 )
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendJobToTomorrowChargers implements Job {

    private final SpreadSheetsService spreadSheetsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("앙앙아아");
    }
}
