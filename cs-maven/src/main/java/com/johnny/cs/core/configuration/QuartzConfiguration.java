package com.johnny.cs.core.configuration;

import com.johnny.cs.core.job.SendJobToChargerForCompletion;
import com.johnny.cs.core.job.today.SendJobToTodayHolidayCharger;
import com.johnny.cs.core.job.today.SendJobToTodayNighttimeCharger;
import com.johnny.cs.core.job.today.SendJobToTodayWeeklyChargers;
import com.johnny.cs.core.job.tomorrow.SendJobToTomorrowChargers;
import com.johnny.cs.core.job.unreadmail.SendJobForUnRepliedMailOnWeekEnd;
import com.johnny.cs.core.job.unreadmail.SendJobForUnRepliedMailOnWeekly;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

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
    public JobDetail sendJobToTodayHolidayChargerDetail(){
        return JobBuilder.newJob().ofType(SendJobToTodayHolidayCharger.class)
                .storeDurably()
                .withIdentity("SendJobToTodayHolidayCharger")
                .withDescription("Check for Holiday charger")
                .build();
    }

    @Bean
    public JobDetail sendJobToTodayNighttimeChargerDetail() {
        return JobBuilder.newJob().ofType(SendJobToTodayNighttimeCharger.class)
                .storeDurably()
                .withIdentity("SendJobToTodayNighttimeCharger")
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
//        trigger.setCronExpression("* 0/1 9-23 ? * SAT,SUN *");
//        return trigger;
//    }
//
//    @Bean
//    public CronTriggerFactoryBean unRepliedMailOnWeeklyTrigger(JobDetail sendJobForUnRepliedMailOnWeeklyDetail) {
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(sendJobForUnRepliedMailOnWeeklyDetail);
//        trigger.setCronExpression("* 0/1 18-23 ? * MON,TUE,WED,THU,FRI *");
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

    /**
     * 절취선 위의 주석은 미완성
     * *******************
     * 아래 주석은 완성
     */

//    @Bean
//    public CronTriggerFactoryBean todayHolidayChargerTrigger(JobDetail sendJobToTodayHolidayChargerDetail) {
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(sendJobToTodayHolidayChargerDetail);
//        trigger.setCronExpression("59 54 08 ? * * *");
//        return trigger;
//    }

//    @Bean
//    public CronTriggerFactoryBean todayNighttimeChargerTrigger(JobDetail sendJobToTodayNighttimeChargerDetail) {
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(sendJobToTodayNighttimeChargerDetail);
//        trigger.setCronExpression("59 54 17 ? * * *");
//        return trigger;
//    }

//    @Bean
//    public CronTriggerFactoryBean todayWeeklyChargersTrigger(JobDetail sendJobToTodayWeeklyChargersDetail) {
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(sendJobToTodayWeeklyChargersDetail);
//        trigger.setCronExpression("59 59 08 ? * * *");
//        return trigger;
//    }

//    @Bean
//    public CronTriggerFactoryBean tomorrowChargersTrigger(JobDetail sendJobToTomorrowChargersDetail) {
//        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
//        trigger.setJobDetail(sendJobToTomorrowChargersDetail);
//        trigger.setCronExpression("59 59 21 ? * * *");
//        return trigger;
//    }
}
