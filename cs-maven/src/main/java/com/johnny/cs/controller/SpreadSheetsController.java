package com.johnny.cs.controller;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.johnny.cs.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class SpreadSheetsController {

    private final SpreadSheetsService spreadSheetsService;

    private static final String RANGE = "A:D";
    private static final String SPREAD_SHEET_ID = "12E88gPUoIFYPo4KJd1Bby4P9FF_Q9SBmwzN_DkCHGn0";

    @GetMapping("/")
    public ResponseEntity<String> landing() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/get/sheet")
    public ResponseEntity<List<List<Object>>> getSheets() throws IOException, GeneralSecurityException, URISyntaxException {
        Sheets sheetsService = spreadSheetsService.createSheetsService();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREAD_SHEET_ID, RANGE)
                .execute();

        List<List<Object>> values = response.getValues();
        if (CollectionUtils.isEmpty(values)) {
            return ResponseEntity.noContent().build();
        }

        log.info("{}", response);
        log.info("{}", values);
        return ResponseEntity.ok(values);
    }


}
