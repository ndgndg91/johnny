package com.johnny.cs.core.domain.person.today;

import com.johnny.cs.core.domain.person.HolidayCharger;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TodayHolidayCharger {
    private HolidayCharger holidayCharger;

    public static TodayHolidayCharger is(HolidayCharger holidayCharger) {
        TodayHolidayCharger todayHolidayCharger = new TodayHolidayCharger();
        todayHolidayCharger.holidayCharger = holidayCharger;
        return todayHolidayCharger;
    }
}
