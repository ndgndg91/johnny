package com.johnny.cs.core.domain.person.tomorrow;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonIgnore
    public List<Charger> getWeekDayChargers(){
        List<Charger> weekDayChargers = new ArrayList<>(weeklyChargers);
        weekDayChargers.add(rotationCharger);
        weekDayChargers.add(nighttimeCharger);
        return weekDayChargers;

    }

}
