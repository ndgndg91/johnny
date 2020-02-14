package com.johnny.cs.job;

import com.johnny.cs.service.SpreadSheetsService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 당일 오전, 오후 CS 담당자에게 오전 9시에 알림.
 */
@Log4j2
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
            log.error("{}", e);
        }
    }
}
