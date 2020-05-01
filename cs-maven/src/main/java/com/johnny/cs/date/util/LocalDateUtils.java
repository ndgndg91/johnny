package com.johnny.cs.date.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public final class LocalDateUtils {
    private static final Set<String> TEMP_HOLIDAYS_IN_2020 = initTempHolidaysIn2020();
    private static final String MONTH = "월";

    private LocalDateUtils(){}

    private static Set<String> initTempHolidaysIn2020(){
        Set<String> tempHolidays = new HashSet<>();
        tempHolidays.add(LocalDate.of(2020, 5, 1).toString()); //근로자의 날
        return tempHolidays;
    }

    public static boolean isTempHolidayIn2020(LocalDate target) {
        return TEMP_HOLIDAYS_IN_2020.contains(target.toString());
    }

    public static String getTodayString(){
        return String.valueOf(LocalDate.now().getDayOfMonth());
    }

    public static String getMonthOfTomorrowString() {
        return LocalDate.now().plusDays(1).getMonth().getValue() + MONTH;
    }

    public static String getDayOfTomorrowString(){
        return String.valueOf(LocalDate.now().plusDays(1).getDayOfMonth());
    }

    public static String getDayAfterTomorrowString(){
        return String.valueOf(LocalDate.now().plusDays(2).getDayOfMonth());
    }

    public static boolean isWeeklyWorkTime() {
        int hour = LocalDateTime.now().getHour();
        return hour >= 9 && hour < 18;
    }
}
