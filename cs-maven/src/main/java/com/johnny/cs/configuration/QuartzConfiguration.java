package com.johnny.cs.configuration;

import com.johnny.cs.job.*;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

@Configuration
public class QuartzConfiguration {

    @Bean
    public JobDetail sendJobForUnRepliedMailAlarmDetail() {
        return JobBuilder.newJob().ofType(SendJobForUnRepliedMailAlarm.class)
                .storeDurably()
                .withIdentity("SendJobForUnRepliedMailAlarm")
                .withDescription("LineWorks help account check unread email and unReply email")
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
    public JobDetail sendJobToHolidayChargerDetail() {
        return JobBuilder.newJob().ofType(SendJobToHolidayCharger.class)
                .storeDurably()
                .withIdentity("SendJobToHolidayCharger")
                .withDescription("Check for holiday charger")
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

    @Bean
    public CronTriggerFactoryBean unRepliedMailAlarmTrigger(JobDetail sendJobForUnRepliedMailAlarmDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(sendJobForUnRepliedMailAlarmDetail);
        trigger.setCronExpression("0 0/5 * * * ?");
        return trigger;
    }
}
