package com.johnny.cs.core.util;

import com.johnny.cs.core.domain.person.Charger;
import com.johnny.cs.core.domain.person.SixshopWorker;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class PhoneUtils {
    private static final Map<String, String> phones = makeBook();
    private PhoneUtils(){}

    public static Map<String, String> getPhoneBook(){
        return phones;
    }

    private static Map<String, String> makeBook(){
        List<Charger> sixshopWorkers = Arrays.stream(SixshopWorker.values()).map(SixshopWorker::getCharger)
            .collect(Collectors.toList());
        return sixshopWorkers.stream().collect(Collectors.toMap(Charger::getName, Charger::getPhone));
    }

}
