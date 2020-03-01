package com.johnny.cs.core.job.unreadmail;

import com.johnny.cs.date.service.HolidayService;
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
public class SendJobForUnRepliedMailOnWeekly implements Job {

    private final HolidayService holidayService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("휴일 CS 답장하지 않는 메일 체킹");
        if (holidayService.isHoliday(LocalDate.now())) {
            log.info("오늘은 공휴일!");
            return;
        }

        log.info("오늘은 공휴일이 아니군!");
    }
}
