package com.johnny.cs.core.configuration;

import com.johnny.cs.core.job.SendJobToChargerForCompletion;
import com.johnny.cs.core.job.SendJobToNighttimeCharger;
import com.johnny.cs.core.job.SendJobToTodayWeeklyChargers;
import com.johnny.cs.core.job.SendJobToTomorrowChargers;
import com.johnny.cs.core.job.unreadmail.SendJobForUnRepliedMailOnWeekEnd;
import com.johnny.cs.core.job.unreadmail.SendJobForUnRepliedMailOnWeekly;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Slf4j
@Configuration
public class QuartzConfiguration {

    @Bean
    public JobDetail sendJobForUnRepliedMailOnWeekEndDetail() {
        return JobBuilder.newJob().ofType(SendJobForUnRepliedMailOnWeekEnd.class)
                .storeDurably()
                .withIdentity("SendJobForUnRepliedMailOnWeekEnd")
                .withDescription("LineWorks help account check unread email and unReply email on weekend")
                .build();
    }

    @Bean
    public JobDetail sendJobForUnRepliedMailOnWeeklyDetail() {
        return JobBuilder.newJob().ofType(SendJobForUnRepliedMailOnWeekly.class)
                .storeDurably()
                .withIdentity("SendJobForUnRepliedMailOnWeekly")
                .withDescription("LineWorks help account check unread email and unReply email on weekly")
                .build();
    }

    @Bean
    public JobDetail sendJobToChargerForCompletionDetail() {
        return JobBuilder.newJob().ofType(SendJobToChargerForCompletion.class)
                .storeDurably()
                .withIdentity("SendJobToChargerForCompletion")
                .withDescription("Celebration to Completion of CS")
                .build();
    }

    @Bean
    public JobDetail sendJobToNighttimeChargerDetail() {
        return JobBuilder.newJob().ofType(SendJobToNighttimeCharger.class)
                .storeDurably()
                .withIdentity("SendJobToNighttimeCharger")
                .withDescription("Check for NightTime charger")
                .build();
    }

    @Bean
    public JobDetail sendJobToTodayWeeklyChargersDetail() {
        return JobBuilder.newJob().ofType(SendJobToTodayWeeklyChargers.class)
                .storeDurably()
                .withIdentity("SendJobToTodayWeeklyChargers")
                .withDescription("Check for Today charger")
                .build();
    }

    @Bean
    public JobDetail sendJobToTomorrowChargersDetail() {
        return JobBuilder.newJob().ofType(SendJobToTomorrowChargers.class)
                .storeDurably()
                .withIdentity("SendJobToTomorrowChargers")
                .withDescription("Check for Tomorrow charger")
                .build();
    }

//    @Bean
//    public CronTriggerFactoryBean unRepliedMailOnWeekEndTrigger(JobDetail sendJobForUnRepliedMailOnWeekEndDetail) {
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(sendJobForUnRepliedMailOnWeekEndDetail);
//        trigger.setCronExpression("5 * * * * ? *");
//        return trigger;
//    }
//
//    @Bean
//    public CronTriggerFactoryBean unRepliedMailOnWeeklyTrigger(JobDetail sendJobForUnRepliedMailOnWeeklyDetail) {
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(sendJobForUnRepliedMailOnWeeklyDetail);
//        trigger.setCronExpression("10 * * * * ? *");
//        return trigger;
//    }
//
//    @Bean
//    public CronTriggerFactoryBean chargerForCompletionTrigger(JobDetail sendJobToChargerForCompletionDetail) {
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(sendJobToChargerForCompletionDetail);
//        trigger.setCronExpression("15 * * * * ? *");
//        return trigger;
//    }
//
//    @Bean
//    public CronTriggerFactoryBean nighttimeChargerTrigger(JobDetail sendJobToNighttimeChargerDetail) {
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(sendJobToNighttimeChargerDetail);
//        trigger.setCronExpression("20 * * * * ? *");
//        return trigger;
//    }
//
//    @Bean
//    public CronTriggerFactoryBean todayWeeklyChargersTrigger(JobDetail sendJobToTodayWeeklyChargersDetail) {
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(sendJobToTodayWeeklyChargersDetail);
//        trigger.setCronExpression("25 * * * * ? *");
//        return trigger;
//    }

    @Bean
    public CronTriggerFactoryBean tomorrowChargersTrigger(JobDetail sendJobToTomorrowChargersDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(sendJobToTomorrowChargersDetail);
        trigger.setCronExpression("30 * * * * ? *");
//        55 59 21 * * ? *
        return trigger;
    }

//    @Bean
//    public Scheduler unRepliedMailAlarmScheduler(SchedulerFactoryBean factory)
//            throws SchedulerException {
//        Scheduler scheduler = factory.getScheduler();
//        scheduler.start();
//        return scheduler;
//    }
}
