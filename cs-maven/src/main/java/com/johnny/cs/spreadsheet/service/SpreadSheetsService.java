package com.johnny.cs.spreadsheet.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.johnny.cs.alarm.domain.Template;
import com.johnny.cs.core.domain.person.*;
import com.johnny.cs.core.domain.person.today.TodayHolidayCharger;
import com.johnny.cs.core.domain.person.today.TodayNighttimeCharger;
import com.johnny.cs.core.domain.person.today.TodayWeeklyCharger;
import com.johnny.cs.core.domain.person.tomorrow.TomorrowCharger;
import com.johnny.cs.core.util.PhoneUtils;
import com.johnny.cs.date.util.LocalDateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpreadSheetsService {

    private final CredentialService credentialService;

    public TodayHolidayCharger getTodayHolidayCharger() {
        List<List<Object>> values = credentialService.getValues();
        List<String> chargers = getTodayChargers(values);
        return discriminateTodayHolidayCharger(chargers);
    }

    private TodayHolidayCharger discriminateTodayHolidayCharger(List<String> chargers){
        String name = chargers.get(0);
        return TodayHolidayCharger.is(new HolidayCharger(name,
                PhoneUtils.getPhoneBook().get(name),
                Template.SEND_TODAY_HOLIDAY_CHARGER));
    }

    public TodayNighttimeCharger getTodayNighttimeChargers() {
        List<List<Object>> values = credentialService.getValues();
        List<String> chargers = getTodayChargers(values);
        return discriminateTodayNighttimeCharger(chargers);
    }

    private TodayNighttimeCharger discriminateTodayNighttimeCharger(List<String> chargers) {
        String name = chargers.get(4);
        return TodayNighttimeCharger.is(new NighttimeCharger(name,
                PhoneUtils.getPhoneBook().get(name),
                Template.SEND_TODAY_NIGHTTIME_CHARGER));
    }

    public TodayWeeklyCharger getTodayWeeklyChargers() {
        List<List<Object>> values = credentialService.getValues();
        List<String> chargers = getTodayChargers(values);
        return discriminateTodayWeeklyChargers(chargers);
    }

    private TodayWeeklyCharger discriminateTodayWeeklyChargers(List<String> chargers){
        List<WeeklyCharger> weeklyChargers = chargers.stream()
                .filter(CSTeam::isNotCSTeam)
                .map(c -> new WeeklyCharger(c,
                        PhoneUtils.getPhoneBook().get(c),
                        Template.SEND_TODAY_WEEKLY_CHARGER))
                .collect(Collectors.toUnmodifiableList());
        return TodayWeeklyCharger.is(weeklyChargers);
    }

    public TomorrowCharger getTomorrowChargers() {
        List<List<Object>> values = credentialService.getValues();
        List<String> chargers = getTomorrowChargers(values);
        return discriminateTomorrowChargers(chargers);
    }

    private TomorrowCharger discriminateTomorrowChargers(List<String> chargers) {
        if (isOneCharger(chargers)) {
            return new TomorrowCharger(new HolidayCharger(chargers.get(0),
                    PhoneUtils.getPhoneBook().get(chargers.get(0)),
                    Template.SEND_TOMORROW_HOLIDAY_CHARGER)
            );
        }

        int totalChargersCount = chargers.size();
        List<WeeklyCharger> weeklyChargers = chargers.subList(0, totalChargersCount-2)
                .stream()
                .filter(CSTeam::isNotCSTeam)
                .map(c -> new WeeklyCharger(c,
                        PhoneUtils.getPhoneBook().get(c),
                        Template.SEND_TOMORROW_WEEKLY_CHARGER))
                .collect(Collectors.toUnmodifiableList());
        RotationCharger rotationCharger = new RotationCharger(chargers.get(totalChargersCount-2),
                PhoneUtils.getPhoneBook().get(chargers.get(totalChargersCount-2)),
                Template.SEND_TOMORROW_WEEKLY_CHARGER);

        NighttimeCharger nighttimeCharger = new NighttimeCharger(chargers.get(totalChargersCount-1),
                PhoneUtils.getPhoneBook().get(chargers.get(totalChargersCount-1)),
                Template.SEND_TOMORROW_NIGHTTIME_CHARGER);
        return TomorrowCharger.builder()
                .weeklyChargers(weeklyChargers)
                .rotationCharger(rotationCharger)
                .nighttimeCharger(nighttimeCharger)
                .build();
    }

    private boolean isOneCharger(List<String> chargers) {
        return chargers.size() == 1;
    }

    public List<String> getSpecificDayChargers(byte month, byte day) {
        List<List<Object>> values = credentialService.getValues();
        return getSpecificDayChargers(values, month, day)
                .stream()
                .filter(CSTeam::isNotCSTeam)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
    }

    private List<String> getTodayChargers(List<List<Object>> values) {
        String today = LocalDateUtils.getTodayString();
        String tomorrow = LocalDateUtils.getTomorrowString();
        return getChargersByDay(values, today, tomorrow);
    }

    private List<String> getTomorrowChargers(List<List<Object>> values) {
        String tomorrow = LocalDateUtils.getTomorrowString();
        String dayAfterTomorrow = LocalDateUtils.getDayAfterTomorrowString();
        return getChargersByDay(values, tomorrow, dayAfterTomorrow);
    }

    private List<String> getSpecificDayChargers(List<List<Object>> values, byte month, byte day) {
        LocalDate targetDate = LocalDate.now().withMonth(month).withDayOfMonth(day);
        String targetDay = String.valueOf(targetDate.getDayOfMonth());
        String dayAfterTargetDay = String.valueOf(targetDate.plusDays(1).getDayOfMonth());
        return getChargersByDay(values, targetDay, dayAfterTargetDay);
    }

    private List<String> getChargersByDay(List<List<Object>> values, String targetDay, String dayAfterTargetDay) {
        List<Object> rowContainingTomorrow = getRowContainingDay(values, targetDay);
        return extractDayChargers(rowContainingTomorrow, targetDay, dayAfterTargetDay);
    }

    private List<String> extractDayChargers(List<Object> rowContainingToday, String targetDay, String dayAfterTargetDay) {
        List<String> chargers = Lists.newArrayList();
        int startIdx = 0;
        int endIdx = rowContainingToday.size();
        for (int i = 0; i < rowContainingToday.size(); i++) {
            if (rowContainingToday.get(i).equals(targetDay)) {
                startIdx = i + 1;
            }

            if (rowContainingToday.get(i).equals(dayAfterTargetDay)) {
                endIdx = i;
                break;
            }
        }

        for (int i = startIdx; i < endIdx; i++) {
            String canBeTwoPeople = String.valueOf(rowContainingToday.get(i));
            if (isTwoPeople(canBeTwoPeople)) {
                addTwoChargers(canBeTwoPeople, chargers);
                continue;
            }

            chargers.add(canBeTwoPeople);
        }

        return chargers;
    }

    private boolean isTwoPeople(String canBeTwoPeople) {
        return canBeTwoPeople.contains("\n/") && canBeTwoPeople.split("\n/").length >= 2;
    }

    private void addTwoChargers(Object maybeTwoChargers, List<String> chargers) {
        String[] twoChargers = String.valueOf(maybeTwoChargers).split("\n/");
        chargers.addAll(Arrays.asList(twoChargers));
    }

    private List<Object> getRowContainingDay(List<List<Object>> values, String day) {
        List<Object> todayRow = new ArrayList<>();
        for (List<Object> row : values) {
            if (row.contains(day)) {
                todayRow = row;
                break;
            }
        }

        return todayRow;
    }
}
