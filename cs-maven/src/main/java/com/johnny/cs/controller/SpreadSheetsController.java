package com.johnny.cs.controller;

import com.johnny.cs.service.HolidayService;
import com.johnny.cs.service.SpreadSheetsService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.List;
import javax.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j2
@Controller
@RequiredArgsConstructor
public class SpreadSheetsController {

    private final SpreadSheetsService spreadSheetsService;

    private final HolidayService holidayService;

    @GetMapping("/")
    public ResponseEntity<String> landing() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/get/todayWeeklyChargers")
    public ResponseEntity<List<String>> getTodayChargers() throws IOException, GeneralSecurityException, URISyntaxException {
        List<String> todayWeeklyChargers = spreadSheetsService.getTodayWeeklyChargers();
        if (CollectionUtils.isEmpty(todayWeeklyChargers)) {
            return ResponseEntity.noContent().build();
        }

        log.info("{}", todayWeeklyChargers);
        return ResponseEntity.ok(todayWeeklyChargers);
    }

    @GetMapping("/get/tomorrowChargers")
    public ResponseEntity<List<String>> getTomorrowChargers() throws GeneralSecurityException, IOException, URISyntaxException {
        List<String> tomorrowChargers = spreadSheetsService.getTomorrowChargers();
        if (CollectionUtils.isEmpty(tomorrowChargers)) {
            return ResponseEntity.noContent().build();
        }

        log.info("{}", tomorrowChargers);
        return ResponseEntity.ok(tomorrowChargers);
    }

    @GetMapping("/get/{month}/{day}")
    public ResponseEntity<List<String>> getChargersOfSpecificDay(@PathVariable byte month, @PathVariable byte day)
        throws GeneralSecurityException, IOException, URISyntaxException {
        List<String> specificDayChargers = spreadSheetsService.getSpecificDayChargers(month, day);
        if (CollectionUtils.isEmpty(specificDayChargers)) {
            return ResponseEntity.noContent().build();
        }

        log.info("{}", specificDayChargers);
        return ResponseEntity.ok(specificDayChargers);
    }

    @GetMapping("/holiday/{month}/{day}")
    public ResponseEntity<Boolean> isHoliday(@PathVariable int month, @PathVariable int day)
        throws IOException, JAXBException {
        return ResponseEntity
            .ok(holidayService.isHoliday(LocalDate.now().withMonth(month).withDayOfMonth(day)));
    }
}
