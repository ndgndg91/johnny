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
import com.johnny.cs.domain.Month;
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
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import org.springframework.util.ResourceUtils;

@Log4j2
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

    public List<List<Object>> getValues() throws GeneralSecurityException, IOException, URISyntaxException {
        Sheets sheetsService = getCSChargersSheets();
        ValueRange response = sheetsService.spreadsheets().values()
            .get(spreadSheetId, getRange())
            .execute();

        log.info("{}", response);
        getTodayChargers(response.getValues());
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

    private void getTodayChargers(List<List<Object>> values){
        LocalDate now = LocalDate.now();
        String day = String.valueOf(now.getDayOfMonth());
        List<Object> todayRow = getTodayRow(values, day);
        log.info("whatthefuck");
        for (Object o : todayRow) {
            log.info(o);
        }
    }

    private List<Object> getTodayRow(List<List<Object>> values, String day){
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

    private void dd(){

    }

}
