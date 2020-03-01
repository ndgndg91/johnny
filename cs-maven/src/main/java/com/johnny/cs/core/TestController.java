package com.johnny.cs.core;

import com.johnny.cs.core.aws.AwsSQSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    @Value("${aws.test.sqs.queueName.bizm}")
    private String queueName;

    private final AwsSQSService awsSQSService;

    @GetMapping("/aws/test")
    public ResponseEntity<String> awsTest(){
        String amazonSQS = awsSQSService.getQueueUrl(queueName);
        log.info("{}", amazonSQS);
        return ResponseEntity.ok(amazonSQS);
    }

}
