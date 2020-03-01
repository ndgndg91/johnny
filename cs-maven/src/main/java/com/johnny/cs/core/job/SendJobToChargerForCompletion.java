package com.johnny.cs.core.job;

import com.johnny.cs.spreadsheet.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * CS 일정이 끝난 뒤 수고 인사 ( 야간, 휴일 )
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendJobToChargerForCompletion implements Job {

    private final SpreadSheetsService spreadSheetsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("야간 휴일 CS 종료 시각인 밤 11시에 알람 보낼곤대~");
    }
}
