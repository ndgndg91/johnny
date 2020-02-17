package com.johnny.cs.controller;

import com.johnny.cs.service.LineWorksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Log4j2
@RestController
@RequiredArgsConstructor
public class LineWorksController {

    private final LineWorksService lineWorksService;

    @GetMapping("/line/sixshop")
    public ResponseEntity<Void> lineXSixShop(){
       log.info("들어왔따잉");
       return ResponseEntity.ok().build();
    }

    @GetMapping("/line/test")
    public ResponseEntity<Void> lineTest() throws IOException {
        lineWorksService.callApi();
        return ResponseEntity.ok().build();
    }
}
