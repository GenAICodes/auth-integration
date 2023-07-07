
package com.auth.integration.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetsService {

    @Value("${google.sheets.api.credentials-file-path}")
    private String credentialsFilePath;

    @Value("${google.sheets.api.sheet-id}")
    private String sheetId;

    private final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    public void writeToSheet(String email) throws IOException, GeneralSecurityException {
        Credential credential = getCredentials();
        Sheets sheetsService = getSheetsService(credential);

        ValueRange valueRange = new ValueRange();
        valueRange.setValues(Collections.singletonList(Collections.singletonList(email)));

        sheetsService.spreadsheets().values()
                .append(sheetId, "Sheet1", valueRange)
                .setValueInputOption("RAW")
                .execute();
    }

    private Credential getCredentials() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return GoogleCredential.fromStream(getClass().getResourceAsStream(credentialsFilePath))
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
    }

    private Sheets getSheetsService(Credential credential) throws IOException {
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
                .setApplicationName("Google Sheets Integration")
                .build();
    }
}
