package com.johnny.cs.controller;

import com.johnny.cs.service.SpreadSheetsService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
public class SpreadSheetsController {

    private final SpreadSheetsService spreadSheetsService;

    @GetMapping("/")
    public ResponseEntity<String> landing() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/get/sheet")
    public ResponseEntity<List<List<Object>>> getSheets() throws IOException, GeneralSecurityException, URISyntaxException {
        List<List<Object>> values = spreadSheetsService.getValues();
        if (CollectionUtils.isEmpty(values)) {
            return ResponseEntity.noContent().build();
        }

        log.info("{}", values);
        return ResponseEntity.ok(values);
    }
}
