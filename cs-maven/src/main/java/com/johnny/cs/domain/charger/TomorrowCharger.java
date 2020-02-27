package com.johnny.cs.domain.charger;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TomorrowCharger {
    private boolean isHoliday;
    @Getter
    private HolidayCharger holidayCharger;
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

    public List<WeeklyCharger> getWeeklyChargers(){
        return weeklyChargers
                .stream()
                .filter(CSTeam::isNotCSTeam)
                .collect(Collectors.toUnmodifiableList());
    }
}
