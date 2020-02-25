package com.johnny.cs.job;

import com.johnny.cs.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

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
        try {
            spreadSheetsService.getTodayWeeklyChargers();
        } catch (GeneralSecurityException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
