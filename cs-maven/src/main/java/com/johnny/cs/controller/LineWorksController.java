package com.johnny.cs.controller;

import com.johnny.cs.domain.line.request.AuthorizationCodeRequest;
import com.johnny.cs.service.LineWorksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Log4j2
@RestController
@RequiredArgsConstructor
public class LineWorksController {

    @Value("${api.auth.state}")
    private String state;

    private final LineWorksService lineWorksService;

    @GetMapping("/line/sixshop")
    public ResponseEntity<Void> lineXSixShop(AuthorizationCodeRequest request) throws IOException {
       log.info("{}", request);
       if ( ! request.getState().equals(state)) {
           return ResponseEntity.badRequest().build();
       }

       lineWorksService.getAccessToken(request.getCode());
       return ResponseEntity.ok().build();
    }

    @GetMapping("/line/test")
    public ResponseEntity<Void> lineTest() throws InterruptedException {
        lineWorksService.getAccessCode();
        return ResponseEntity.ok().build();
    }
}
