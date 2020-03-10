package com.johnny.cs.core.job;

import com.johnny.cs.date.service.HolidayService;
import com.johnny.cs.spreadsheet.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public final class TestJob implements Job {

    private final HolidayService holidayService;

    private final SpreadSheetsService spreadSheetsService;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("=================================================================테스트 job 시작=================================================================");

        log.info("오늘은 휴일인가 ? : {}",holidayService.isHoliday(LocalDate.now()));
        log.info("내일 담당자들 : {}", spreadSheetsService.getTomorrowChargers());
        log.info("오늘 야간 담당자 : {}", spreadSheetsService.getTodayNighttimeChargers());
        log.info("오늘 주간 담당자들 : {}",spreadSheetsService.getTodayWeeklyChargers());
        log.info("오늘 휴일 담당자 : {}", spreadSheetsService.getTodayHolidayCharger());

        log.info("=================================================================테스트 job 종료=================================================================");
    }
}
