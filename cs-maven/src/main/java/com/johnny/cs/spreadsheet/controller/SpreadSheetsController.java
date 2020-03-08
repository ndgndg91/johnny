package com.johnny.cs.spreadsheet.controller;

import com.johnny.cs.alarm.service.AlarmService;
import com.johnny.cs.core.domain.person.today.TodayHolidayCharger;
import com.johnny.cs.core.domain.person.today.TodayWeeklyCharger;
import com.johnny.cs.core.domain.person.tomorrow.TomorrowCharger;
import com.johnny.cs.date.service.HolidayService;
import com.johnny.cs.spreadsheet.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
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
    public ResponseEntity<TodayWeeklyCharger> getTodayChargers() {
        TodayWeeklyCharger todayWeeklyChargers = spreadSheetsService.getTodayWeeklyChargers();
        if (Objects.isNull(todayWeeklyChargers)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(todayWeeklyChargers);
    }

    @GetMapping("/get/todayHolidayCharger")
    public ResponseEntity<TodayHolidayCharger> getTodayHolidayCharger(){
        TodayHolidayCharger todayHolidayCharger = spreadSheetsService.getTodayHolidayCharger();
        return ResponseEntity.ok(todayHolidayCharger);
    }

    @GetMapping("/get/tomorrowChargers")
    public ResponseEntity<TomorrowCharger> getTomorrowChargers() {
        TomorrowCharger tomorrowChargers = spreadSheetsService.getTomorrowChargers();
        if (ObjectUtils.isEmpty(tomorrowChargers)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tomorrowChargers);
    }

    @GetMapping("/get/{month}/{day}")
    public ResponseEntity<List<String>> getChargersOfSpecificDay(@PathVariable byte month, @PathVariable byte day) {
        List<String> specificDayChargers = spreadSheetsService.getSpecificDayChargers(month, day);
        if (CollectionUtils.isEmpty(specificDayChargers)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(specificDayChargers);
    }

    @GetMapping("/holiday/{month}/{day}")
    public ResponseEntity<Boolean> isHoliday(@PathVariable int month, @PathVariable int day) {
        return ResponseEntity
            .ok(holidayService.isHoliday(LocalDate.now().withMonth(month).withDayOfMonth(day)));
    }
}
