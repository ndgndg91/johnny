package com.johnny.cs.core.configuration;

import com.johnny.cs.core.job.SendJobToChargerForCompletion;
import com.johnny.cs.core.job.TestJob;
import com.johnny.cs.core.job.today.SendJobToTodayHolidayCharger;
import com.johnny.cs.core.job.today.SendJobToTodayNighttimeCharger;
import com.johnny.cs.core.job.today.SendJobToTodayWeeklyChargers;
import com.johnny.cs.core.job.tomorrow.SendJobToTomorrowChargers;
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

    /**
     * 테스트 잡
     */
    @Bean
    public JobDetail testJobDetail(){
        return JobBuilder.newJob().ofType(TestJob.class)
                .storeDurably()
                .withIdentity("test Job")
                .withDescription("모든 메서드 테스트")
                .build();
    }

    @Bean
    public CronTriggerFactoryBean testTrigger(JobDetail testJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(testJobDetail);
        trigger.setCronExpression("0 0/30 * 1/1 * ? *");
        return trigger;
    }

    @Bean
    public CronTriggerFactoryBean todayHolidayChargerTrigger(JobDetail sendJobToTodayHolidayChargerDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(sendJobToTodayHolidayChargerDetail);
        trigger.setCronExpression("59 54 08 ? * * *");
        return trigger;
    }

    @Bean
    public CronTriggerFactoryBean todayNighttimeChargerTrigger(JobDetail sendJobToTodayNighttimeChargerDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(sendJobToTodayNighttimeChargerDetail);
        trigger.setCronExpression("59 54 17 ? * * *");
        return trigger;
    }

    @Bean
    public CronTriggerFactoryBean todayWeeklyChargersTrigger(JobDetail sendJobToTodayWeeklyChargersDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(sendJobToTodayWeeklyChargersDetail);
        trigger.setCronExpression("59 59 08 ? * * *");
        return trigger;
    }

    @Bean
    public CronTriggerFactoryBean tomorrowChargersTrigger(JobDetail sendJobToTomorrowChargersDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(sendJobToTomorrowChargersDetail);
        trigger.setCronExpression("59 59 21 ? * * *");
        return trigger;
    }

    @Bean
    public CronTriggerFactoryBean chargerForCompletionTrigger(JobDetail sendJobToChargerForCompletionDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(sendJobToChargerForCompletionDetail);
        trigger.setCronExpression("59 59 22 ? * * *");
        return trigger;
    }

    @Bean
    public Scheduler alarmScheduler(SchedulerFactoryBean factory)
            throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        return scheduler;
    }
}
