package com.johnny.cs.alarm.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class BizmUtils {

    private static final Set<String> keys = new HashSet<>();

    private BizmUtils(){}

    public static String generateKey() {
        String johnnyKey;
        do {
            johnnyKey = UUID.randomUUID().toString().substring(0,20);
        } while (keys.contains(johnnyKey));

        keys.add(johnnyKey);
        return johnnyKey;
    }

}
