package com.johnny.cs;

import com.johnny.cs.domain.Month;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.*;

@SpringBootTest
class CsApplicationTests {

    @Test
    void contextLoads() throws FileNotFoundException {
        LocalDate now = LocalDate.now();
        System.out.println(now.getDayOfMonth());
    }

}
