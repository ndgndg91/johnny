package com.johnny.cs;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.johnny.cs.job.SendJobForUnRepliedMailAlarm;
import org.junit.jupiter.api.Test;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@SpringBootTest
class CsApplicationTests {

    @Test
    void contextLoads() throws FileNotFoundException {
        LocalDate now = LocalDate.now();
        System.out.println(now.getDayOfMonth());
    }

    @Test
    public void dateTest() throws  Exception{
        String dateString = "20200101";
        LocalDate parsed = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("uuuuMMdd"));
        System.out.println(parsed);
    }
}
