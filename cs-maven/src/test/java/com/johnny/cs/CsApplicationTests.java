package com.johnny.cs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
