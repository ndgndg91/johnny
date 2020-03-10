package com.johnny.cs;

import com.johnny.cs.alarm.util.BizmUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
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

}
