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
import com.johnny.cs.domain.charger.Month;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class CredentialService {

    @Value("${google.service.account.email}")
    private String serviceAccountEmail;
    @Value("${google.key.file.location}")
    private String keyFileLocation;

    @Value("${google.sheet.id}")
    private String spreadSheetId;
    @Value("${google.sheet.range}")
    private String range;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String APPLICATION_NAME = "johnny";

    public List<List<Object>> getValues() {
        Sheets sheets = getCSChargersSheets();
        ValueRange response = getValueRange(sheets);
        return response.getValues();
    }

    private ValueRange getValueRange(Sheets sheets){
        ValueRange response = null;
        try {
            response = sheets.spreadsheets().values()
                    .get(spreadSheetId, getRange())
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Sheets getCSChargersSheets() {
        HttpTransport httpTransport = getHttpTransport();

        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        Credential credential = getCredentials();

        return new Sheets.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private HttpTransport getHttpTransport() {
        HttpTransport httpTransport = null;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpTransport;
    }

    private Credential getCredentials() {
        File file = Objects.requireNonNull(getKeyFile(), "Key File 가져오기 실패)");
        URL fileURL = Objects.requireNonNull(getFileUrl(file), "File URL 가져오기 실패");

        HttpTransport httpTransport = getHttpTransport();

        return buildCredential(httpTransport, fileURL);
    }

    private File getKeyFile() {
        File file = null;
        try {
            file = ResourceUtils.getFile(keyFileLocation);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return file;
    }

    private URL getFileUrl(File file) {
        URL fileURL = null;
        try {
            fileURL = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return fileURL;
    }

    private Credential buildCredential(HttpTransport httpTransport, URL fileURL) {
        File p12File = createP12File(fileURL);

        GoogleCredential credential = null;
        try {
            credential = new GoogleCredential.Builder()
                    .setTransport(httpTransport)
                    .setJsonFactory(JSON_FACTORY)
                    .setServiceAccountId(serviceAccountEmail)
                    .setServiceAccountPrivateKeyFromP12File(p12File)
                    .setServiceAccountScopes(SCOPES)
                    .build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return credential;
    }

    private File createP12File(URL fileURL) {
        File p12File = null;
        try {
            p12File = new File(fileURL.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return p12File;
    }

    private String getRange() {
        String[] rangeTokens = range.split(":");
        rangeTokens[0] = rangeTokens[0] + getRowIndex();
        return String.join(":", rangeTokens);
    }

    private String getRowIndex() {
        return String.valueOf(Month.getRowIndex());
    }
}
