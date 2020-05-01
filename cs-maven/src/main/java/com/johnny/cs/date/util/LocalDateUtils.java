package com.johnny.cs.date.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class LocalDateUtils {
    private static final String MONTH = "월";

    private LocalDateUtils(){}

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
