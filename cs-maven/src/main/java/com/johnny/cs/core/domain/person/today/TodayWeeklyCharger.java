package com.johnny.cs.core.domain.person.today;

import com.johnny.cs.core.domain.person.WeeklyCharger;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class TodayWeeklyCharger {
    private List<WeeklyCharger> weeklyChargers;

    public static TodayWeeklyCharger is(List<WeeklyCharger> weeklyChargers){
        TodayWeeklyCharger todayWeeklyCharger = new TodayWeeklyCharger();
        todayWeeklyCharger.weeklyChargers = weeklyChargers;
        return todayWeeklyCharger;
    }
}
