package com.johnny.cs;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CsApplicationTests {

    @Test
    void contextLoads() throws FileNotFoundException {
        LocalDate now = LocalDate.now();
        System.out.println(now.getDayOfMonth());
    }
}
