package com.johnny.cs.alarm.util;

import java.util.UUID;

public final class BizmUtils {

    private BizmUtils(){}

    public static String generateKey(){
        return UUID.randomUUID().toString();
    }

}
