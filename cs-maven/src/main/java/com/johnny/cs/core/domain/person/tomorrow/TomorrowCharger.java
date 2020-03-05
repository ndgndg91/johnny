package com.johnny.cs.core.domain.person.tomorrow;

import java.util.ArrayList;
import java.util.List;

import com.johnny.cs.core.domain.person.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TomorrowCharger {
    private boolean isHoliday;
    @Getter
    private HolidayCharger holidayCharger;
    @Getter
    private List<WeeklyCharger> weeklyChargers;
    @Getter
    private RotationCharger rotationCharger;
    @Getter
    private NighttimeCharger nighttimeCharger;

    public TomorrowCharger(HolidayCharger holidayCharger){
        this.isHoliday = true;
        this.holidayCharger = holidayCharger;
    }

    public boolean isTomorrowHoliday(){
        return isHoliday;
    }

    public List<Charger> getWeekDayChargers(){
        List<Charger> weekDayChargers = new ArrayList<>(weeklyChargers);
        weekDayChargers.add(rotationCharger);
        weekDayChargers.add(nighttimeCharger);
        return weekDayChargers;

    }

}
