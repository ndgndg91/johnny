package com.johnny.cs.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.johnny.cs.alarm.domain.Bizm;
import com.johnny.cs.alarm.domain.Template;
import com.johnny.cs.alarm.util.BizmUtils;
import com.johnny.cs.core.aws.AwsSQSService;
import com.johnny.cs.core.util.JacksonUtils;
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

    @Value("${aws.sqs.queueName.bizm}")
    private String queueName;

    @Value("${kakao.sendType}")
    private String sendType;


    @Value("${kakao.profileKey}")
    private String profileKey;

    @Value("${kakao.phone}")
    private String sendPhone;

    private final AwsSQSService awsSQSService;

    @GetMapping("/aws/test")
    public ResponseEntity<String> awsTest() throws JsonProcessingException {
        String amazonSQS = awsSQSService.getQueueUrl(queueName);
        log.info("{}", amazonSQS);
        Bizm bizm = Bizm.builder()
            .sendType(sendType)
            .msgId(BizmUtils.generateKey())
            .profileKey(profileKey)
            .sendPhone(sendPhone)
            .receiverNum("01072255198")
            .templateCode(Template.SEND_CS_END_CHARGER.getTemplateCode())
            .sendContent(Template.SEND_CS_END_CHARGER.getPattern())
            .build();

        awsSQSService.sendMessage(queueName, JacksonUtils.getMapper().writeValueAsString(bizm));
        return ResponseEntity.ok(amazonSQS);
    }

}
