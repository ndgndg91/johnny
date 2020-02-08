package com.johnny.cs.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class SpreadSheetsService {
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_EMAIL = "johnny@johnny-267608.iam.gserviceaccount.com";
    private static final String KEY_FILE_LOCATION = "classpath:johnny-267608-a456f7e05a0b.p12";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    public Sheets createSheetsService() throws IOException, GeneralSecurityException, URISyntaxException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        Credential credential = getCredentials();

        return new Sheets.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName("johnny")
                .build();
    }

    public Credential getCredentials() throws URISyntaxException, IOException, GeneralSecurityException {
        File file = ResourceUtils.getFile(KEY_FILE_LOCATION);
        URL fileURL = file.toURI().toURL();

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
                .setServiceAccountPrivateKeyFromP12File(new File(fileURL.toURI()))
                .setServiceAccountScopes(SCOPES)
                .build();
    }
}
