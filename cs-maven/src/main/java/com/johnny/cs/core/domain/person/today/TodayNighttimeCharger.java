package com.johnny.cs.core.domain.person.today;

import com.johnny.cs.core.domain.person.NighttimeCharger;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TodayNighttimeCharger {
    private NighttimeCharger nighttimeCharger;

    public static TodayNighttimeCharger is(NighttimeCharger nighttimeCharger) {
        TodayNighttimeCharger todayNighttimeCharger = new TodayNighttimeCharger();
        todayNighttimeCharger.nighttimeCharger = nighttimeCharger;
        return todayNighttimeCharger;
    }
}
