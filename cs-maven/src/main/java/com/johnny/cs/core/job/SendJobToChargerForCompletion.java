package com.johnny.cs.core.job;

import com.johnny.cs.alarm.domain.Template;
import com.johnny.cs.alarm.service.AlarmService;
import com.johnny.cs.core.domain.person.today.TodayHolidayCharger;
import com.johnny.cs.core.domain.person.today.TodayNighttimeCharger;
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
 * CS 일정이 끝난 뒤 수고 인사 ( 야간, 휴일 )
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendJobToChargerForCompletion implements Job {

    private final SpreadSheetsService spreadSheetsService;

    private final HolidayService holidayService;

    private final AlarmService alarmService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (holidayService.isHoliday(LocalDate.now())) {
            TodayHolidayCharger todayHolidayCharger = spreadSheetsService.getTodayHolidayCharger(Template.SEND_CS_JUST_END_CHARGER);
            alarmService.sendAlarm(todayHolidayCharger);
            log.info("job 완료");
            return;
        }

        TodayNighttimeCharger todayNighttimeChargers = spreadSheetsService.getTodayNighttimeChargers(Template.SEND_CS_JUST_END_CHARGER);
        alarmService.sendAlarm(todayNighttimeChargers);
        log.info("job 완료");
    }
}
