package com.johnny.cs.core.job.today;

import com.johnny.cs.alarm.service.AlarmService;
import com.johnny.cs.core.domain.person.today.TodayHolidayCharger;
import com.johnny.cs.date.service.HolidayService;
import com.johnny.cs.spreadsheet.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 휴일 5분 전 담당자에게 알림.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendJobToTodayHolidayCharger implements Job {

    private final SpreadSheetsService spreadSheetsService;

    private final HolidayService holidayService;

    private final AlarmService alarmService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if ( ! holidayService.isHoliday(LocalDate.now())) {
            return;
        }

        TodayHolidayCharger todayHolidayCharger = spreadSheetsService.getTodayHolidayCharger();
        alarmService.sendAlarm(todayHolidayCharger);
    }
}
