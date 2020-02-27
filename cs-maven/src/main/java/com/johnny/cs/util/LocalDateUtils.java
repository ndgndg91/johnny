package com.johnny.cs.util;

import java.time.LocalDate;

public final class LocalDateUtils {

    private LocalDateUtils(){}

    public static String getTodayString(){
        return String.valueOf(LocalDate.now().getDayOfMonth());
    }

    public static String getTomorrowString(){
        return String.valueOf(LocalDate.now().plusDays(1).getDayOfMonth());
    }

    public static String getDayAfterTomorrowString(){
        return String.valueOf(LocalDate.now().plusDays(2).getDayOfMonth());
    }

    public static LocalDate getTomorrow() {
        return LocalDate.now().plusDays(1);
    }

}
