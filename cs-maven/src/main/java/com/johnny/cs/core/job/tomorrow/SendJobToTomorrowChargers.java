package com.johnny.cs.core.job.tomorrow;

import com.johnny.cs.alarm.service.AlarmService;
import com.johnny.cs.core.domain.person.tomorrow.TomorrowCharger;
import com.johnny.cs.spreadsheet.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 내일 오전, 오후, 휴일 CS 담당자에게 알림. ( 오전, 오후, 야간, 휴일 )
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendJobToTomorrowChargers implements Job {

    private final SpreadSheetsService spreadSheetsService;

    private final AlarmService alarmService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        TomorrowCharger tomorrowChargers = spreadSheetsService.getTomorrowChargers();
        log.info("{}", tomorrowChargers);
        alarmService.sendAlarm(tomorrowChargers);
        log.info("job 완료");
    }
}
