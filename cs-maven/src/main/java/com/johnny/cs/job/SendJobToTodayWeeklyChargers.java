package com.johnny.cs.job;

import com.johnny.cs.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 당일 오전, 오후 CS 담당자에게 오전 9시에 알림.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendJobToTodayWeeklyChargers implements Job {

    private final SpreadSheetsService spreadSheetsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<String> todayWeeklyChargers = spreadSheetsService.getTodayWeeklyChargers();
        log.info("오늘의 담당자!!");
        log.info("{}", todayWeeklyChargers);
        log.info("오늘의 담당자!!");
    }
}
