package com.johnny.cs.core.job.unreadmail;

import com.johnny.cs.date.service.HolidayService;
import com.johnny.cs.date.util.LocalDateUtils;
import com.johnny.cs.line.service.LineWorksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 답장 보내지 않은 메일이 30분 지났을 때 알림 ( 주말 )
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SendJobForUnRepliedMail implements Job {

    private final HolidayService holidayService;

    private final LineWorksService lineWorksService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        boolean nowIsWeeklyWorkTime = LocalDateUtils.isWeeklyWorkTime();
        boolean todayIsHoliday = holidayService.isHoliday(LocalDate.now());
        boolean todayIsWeekDay = ! todayIsHoliday;

        //평일 09:00 ~ 18:00 아무것도 안한다.
        if (todayIsWeekDay && nowIsWeeklyWorkTime) {
            log.info("오늘은 평일이고 근무시간이라서 메일 체킹은 하지 않는다!!");
            return;
        }

        //평일 18:00 ~ 23:00 와 휴일 일 때는 09:00 ~ 23:00 일 때는 메일 체킹을 한다.
        //답장 보내지 않은 메일이 있는 경우 현재 담당자와 동호님에게 알림을 보낸다.
        lineWorksService.unRepliedMailCheck();
        log.info("평일 야간 CS 시간 혹은 휴일 CS 시간 중 답장 보내지 않은 메일 체킹하는 job 완료!");
    }
}
