package com.johnny.cs.core.job.unreadmail;

import com.johnny.cs.date.service.HolidayService;
import com.johnny.cs.line.service.LineWorksService;
import com.johnny.cs.spreadsheet.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 답장 보내지 않은 메일이 30분 지났을 때 알림 ( 야간, 휴일 )
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendJobForUnRepliedMailOnWeekEnd implements Job {

    private final SpreadSheetsService spreadSheetsService;

    private final HolidayService holidayService;

    private final LineWorksService lineWorksService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("평일 야간 CS 답장하지 않는 메일 체킹");
    }
}
