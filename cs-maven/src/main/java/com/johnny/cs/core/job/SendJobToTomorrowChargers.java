package com.johnny.cs.core.job;

import com.johnny.cs.core.domain.person.Charger;
import com.johnny.cs.core.domain.person.HolidayCharger;
import com.johnny.cs.core.domain.person.NighttimeCharger;
import com.johnny.cs.core.domain.person.RotationCharger;
import com.johnny.cs.core.domain.person.TomorrowCharger;
import com.johnny.cs.core.domain.person.WeeklyCharger;
import com.johnny.cs.spreadsheet.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 내일 오전, 오후, 휴일 CS 담당자에게 알림. ( 오전, 오후, 야간, 휴일 )
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendJobToTomorrowChargers implements Job {

    private final SpreadSheetsService spreadSheetsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Set<Charger> targets = new HashSet<>();
        TomorrowCharger tomorrowChargers = spreadSheetsService.getTomorrowChargers();
        if (tomorrowChargers.isTomorrowHoliday()) {
            HolidayCharger holidayCharger = tomorrowChargers.getHolidayCharger();
            targets.add(holidayCharger);
            return;
        }

        List<WeeklyCharger> weeklyChargers = tomorrowChargers.getWeeklyChargers();
        RotationCharger rotationCharger = tomorrowChargers.getRotationCharger();
        NighttimeCharger nighttimeCharger = tomorrowChargers.getNighttimeCharger();
        targets.addAll(weeklyChargers);
        targets.add(rotationCharger);
        targets.add(nighttimeCharger);


    }


}
