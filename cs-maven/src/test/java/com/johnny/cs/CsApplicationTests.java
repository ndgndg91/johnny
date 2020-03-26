package com.johnny.cs;

import com.johnny.cs.alarm.util.BizmUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    @Test
    public void generateKeyTest() throws Exception {
        Set<String> keys = new HashSet<>();
        for (int i = 0; i < 100; i ++) {
            String key = BizmUtils.generateKey();
            keys.add(key);
            System.out.println(key + "\t" + key.length());
        }

        System.out.println(keys.size());
     }

     @Test
     public void uuid() throws Exception {
         Set<String> keys = new HashSet<>();
         for (int i = 0; i < 100; i++) {
             String johnny = UUID.randomUUID().toString().substring(0,20);
             System.out.println(johnny);
             System.out.println(johnny.length());
             keys.add(johnny);
         }
         System.out.println(keys.size());
    }

    private static final Set<String> keys = new HashSet<>();

    @Test
    public void generate() {
        String johnnyKey;
        do {
            johnnyKey = UUID.randomUUID().toString().substring(0,20);
        } while (keys.contains(johnnyKey));

        System.out.println(johnnyKey);
    }
    
    @Test
    public void hourTest() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();

        //when
        int hour = now.getHour();
        System.out.println(hour);
    }

    @Test
    public void epochTest() throws Exception {
        LocalDateTime mailReceivedTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(1585209628), ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();
        System.out.println(mailReceivedTime);
        System.out.println(now);

        long betweenMinutes = ChronoUnit.MINUTES.between(mailReceivedTime, now);
        long betweenHours = ChronoUnit.HOURS.between(mailReceivedTime, now);
        System.out.println(betweenMinutes);
        System.out.println(betweenHours);

        LocalDateTime plus10Minutes = now.plusMinutes(10);
        long b = ChronoUnit.MINUTES.between(now, plus10Minutes);
        long b2 = ChronoUnit.HOURS.between(now, plus10Minutes);
        System.out.println(b);
        System.out.println(b2);
    }

}
