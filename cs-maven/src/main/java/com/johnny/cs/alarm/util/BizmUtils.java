package com.johnny.cs.alarm.util;

import java.time.LocalDateTime;

public final class BizmUtils {

    private BizmUtils(){}

    public static String generateKey(){
        LocalDateTime now = LocalDateTime.now();
        return generateKey(now);
    }

    private static String generateKey(LocalDateTime now) {
        return new StringBuilder(now.getYear())
                .append(now.getMonth())
                .append(now.getDayOfMonth())
                .append(now.getHour())
                .append(now.getMinute())
                .append(now.getSecond())
                .append(now.getNano())
                .toString();
    }

}
