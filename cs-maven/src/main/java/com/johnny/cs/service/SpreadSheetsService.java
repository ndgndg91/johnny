package com.johnny.cs.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.johnny.cs.domain.CSTeam;
import com.johnny.cs.domain.Month;
import com.johnny.cs.util.LocalDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class SpreadSheetsService {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String APPLICATION_NAME = "johnny";

    @Value("${google.service.account.email}")
    private String serviceAccountEmail;
    @Value("${google.key.file.location}")
    private String keyFileLocation;

    @Value("${google.sheet.range}")
    private String range;
    @Value("${google.sheet.id}")
    private String spreadSheetId;

    public List<String> getTodayWeeklyChargers() throws GeneralSecurityException, IOException, URISyntaxException {
        List<List<Object>> values = getValues();
        return getTodayChargers(values)
            .stream()
            .filter(CSTeam::isNotCSTeam)
            .collect(collectingAndThen(toList(), ImmutableList::copyOf));
    }

    public List<String> getTomorrowChargers() throws GeneralSecurityException, IOException, URISyntaxException {
        List<List<Object>> values = getValues();
        return getTomorrowChargers(values)
            .stream()
            .filter(CSTeam::isNotCSTeam)
            .collect(collectingAndThen(toList(), ImmutableList::copyOf));
    }

    public List<String> getSpecificDayChargers(byte month, byte day)
        throws GeneralSecurityException, IOException, URISyntaxException {
        List<List<Object>> values = getValues();
        return  getSpecificDayChargers(values, month, day)
            .stream()
            .filter(CSTeam::isNotCSTeam)
            .collect(collectingAndThen(toList(), ImmutableList::copyOf));
    }

    private List<List<Object>> getValues() throws GeneralSecurityException, IOException, URISyntaxException {
        Sheets sheetsService = getCSChargersSheets();
        ValueRange response = sheetsService.spreadsheets().values()
            .get(spreadSheetId, getRange())
            .execute();

        log.info("{}", response);
        return response.getValues();
    }

    private Sheets getCSChargersSheets() throws IOException, GeneralSecurityException, URISyntaxException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        Credential credential = getCredentials();

        return new Sheets.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private Credential getCredentials() throws URISyntaxException, IOException, GeneralSecurityException {
        File file = ResourceUtils.getFile(keyFileLocation);
        URL fileURL = file.toURI().toURL();

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(serviceAccountEmail)
                .setServiceAccountPrivateKeyFromP12File(new File(fileURL.toURI()))
                .setServiceAccountScopes(SCOPES)
                .build();
    }

    private String getRange(){
        String[] rangeTokens = range.split(":");
        rangeTokens[0] = rangeTokens[0] + getRowIndex();
        return String.join(":", rangeTokens);
    }

    private String getRowIndex(){
        return String.valueOf(Month.getRowIndex());
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

    private List<String> getSpecificDayChargers(List<List<Object>> values, byte month, byte day){
        LocalDate targetDate = LocalDate.now().withMonth(month).withDayOfMonth(day);
        String targetDay = String.valueOf(targetDate.getDayOfMonth());
        String dayAfterTargetDay = String.valueOf(targetDate.plusDays(1).getDayOfMonth());
        return getChargersByDay(values, targetDay, dayAfterTargetDay);
    }

    private List<String> getChargersByDay(List<List<Object>> values, String targetDay, String dayAfterTargetDay) {
        List<Object> rowContainingTomorrow = getRowContainingDay(values, targetDay);
        List<String> tomorrowChargers = extractDayChargers(rowContainingTomorrow, targetDay, dayAfterTargetDay);
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("{}", tomorrowChargers);
        log.info("{}", tomorrowChargers.size());
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return tomorrowChargers;
    }

    private List<String> extractDayChargers(List<Object> rowContainingToday, String targetDay, String dayAfterTargetDay) {
        List<String> chargers = Lists.newArrayList();
        int startIdx = 0;
        int endIdx = rowContainingToday.size();
        for (int i = 0; i < rowContainingToday.size(); i++) {
            if (rowContainingToday.get(i).equals(targetDay)){
                startIdx = i+1;
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

    private boolean isTwoPeople(String canBeTwoPeople){
        return canBeTwoPeople.contains("\n/") && canBeTwoPeople.split("\n/").length >= 2;
    }

    private void addTwoChargers(Object maybeTwoChargers, List<String> chargers){
        String[] twoChargers = String.valueOf(maybeTwoChargers).split("\n/");
        chargers.addAll(Arrays.asList(twoChargers));
    }

    private List<Object> getRowContainingDay(List<List<Object>> values, String day){
        log.info("{}", day);
        List<Object> todayRow = new ArrayList<>();
        for (List<Object> row : values) {
            if (row.contains(day)) {
                log.info("들어왔어!");
                todayRow = row;
                break;
            }
        }

        return todayRow;
    }

}
