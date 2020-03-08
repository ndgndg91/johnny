package com.johnny.cs;

import com.johnny.cs.alarm.util.BizmUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

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
}
