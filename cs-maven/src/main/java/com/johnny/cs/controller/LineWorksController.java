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

    @Value("${api.stateValue}")
    private String state;

    private final LineWorksService lineWorksService;

    @GetMapping("/line/sixshop")
    public ResponseEntity<Void> lineXSixShop(AuthorizationCodeRequest request){
       log.info("들어왔따잉");
       log.info("{}", request);
       log.info(request.getState().equals(state));
       lineWorksService.getAccessToken(request.getCode());
       return ResponseEntity.ok().build();
    }

    @GetMapping("/line/test")
    public ResponseEntity<Void> lineTest() throws IOException {
        lineWorksService.getAccessCode();
        return ResponseEntity.ok().build();
    }
}
