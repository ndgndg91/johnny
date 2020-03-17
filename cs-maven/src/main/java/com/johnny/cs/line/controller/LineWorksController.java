package com.johnny.cs.line.controller;

import com.johnny.cs.line.domain.request.AuthorizationCodeRequest;
import com.johnny.cs.line.domain.response.AccessCodeResponse;
import com.johnny.cs.line.domain.response.mailread.MailInfo;
import com.johnny.cs.line.service.LineWorksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LineWorksController {

    @Value("${api.auth.state}")
    private String state;

    private final LineWorksService lineWorksService;

    @GetMapping("/line/sixshop")
    public ResponseEntity<Set<MailInfo>> lineXSixShop(final AuthorizationCodeRequest request) throws IOException {
       if ( ! request.getState().equals(state)) {
           return ResponseEntity.badRequest().build();
       }

       AccessCodeResponse accessToken = lineWorksService.getAccessToken(request.getCode());
       if ( ! accessToken.getErrorCode().equals("00")) {
           return ResponseEntity.status(401).build();
       }

        Set<MailInfo> unRepliedMails = lineWorksService.getUnRepliedMails(accessToken.getAccessToken());
        log.info("{}", unRepliedMails);
        return ResponseEntity.ok(unRepliedMails);
    }

    @GetMapping("/line/test")
    public ResponseEntity<String> lineTest() throws InterruptedException {
        lineWorksService.getAccessCode();
        return ResponseEntity.ok("일단 OK");
    }
}
