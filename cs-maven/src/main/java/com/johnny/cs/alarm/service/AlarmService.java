package com.johnny.cs.alarm.service;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.io.CharStreams;
import com.johnny.cs.alarm.domain.Bizm;
import com.johnny.cs.alarm.domain.Template;
import com.johnny.cs.alarm.util.BizmUtils;
import com.johnny.cs.core.domain.person.Charger;
import com.johnny.cs.core.domain.person.HolidayCharger;
import com.johnny.cs.core.domain.person.TomorrowCharger;
import com.johnny.cs.core.util.JacksonUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@EqualsAndHashCode
@Slf4j
@Service
public class AlarmService {

    @Value("${kakao.profileKey}")
    private String profileKey;

    @Value("${kakao.endPoint}")
    private String endPoint;

    @Value("${kakao.bizmUserId")
    private String bizmUserId;

    @Value("${kakao.bizmUserKey}")
    private String bizmUserKey;

    @Value("${kakao.phone}")
    private String sendPhone;

    public void sendAlarm(TomorrowCharger tomorrowChargers) {
        if (tomorrowChargers.isTomorrowHoliday()) {
            HolidayCharger holidayCharger = tomorrowChargers.getHolidayCharger();
            sendAlarm(holidayCharger);
            return;
        }

        List<Charger> weekDayChargers = tomorrowChargers.getWeekDayChargers();
        sendAlarms(weekDayChargers);
    }

    public void sendAlarm(Charger charger) {
        String bizmBody = getBizmBody(charger);
        log.info(bizmBody);
    }

    public <C extends Charger> void sendAlarms(List<C> chargers) {
        for (Charger charger : chargers) {
            String bizmBody = getBizmBody(charger);
            log.info(bizmBody);
            send(bizmBody);
        }
    }

    private String getBizmBody(Charger charger){
        Bizm bizm = Bizm.builder()
            .sendPhone(sendPhone)
            .name(charger.getName())
            .msgId(BizmUtils.generateKey())
            .profileKey(profileKey)
            .templateCode(charger.getTemplate().getTemplateCode())
            .receiverNum("82" + charger.getPhone())
            .message(charger.getTemplate().getPattern())
            .reservedTime("00000000000000")
            .build();
        bizm.reviseChargerName();
        return JacksonUtils.toJson(bizm);
    }

    private void send (String body) {
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Accept", "application/json");
        httpHeaders.set("userId", bizmUserId);
        httpHeaders.set("userKey", bizmUserKey);

        HttpRequest request = Objects.requireNonNull(createRequest(requestFactory, httpHeaders, body));
        HttpResponse response = Objects.requireNonNull(getResponse(request));

        String result = convert(response);
        log.info(result);
    }

    private HttpRequest createRequest(HttpRequestFactory requestFactory, HttpHeaders httpHeaders, String body) {
        HttpRequest request = null;
        try {
            request = requestFactory.buildPostRequest(new GenericUrl(endPoint), ByteArrayContent
                .fromString("application/json", body)).setHeaders(httpHeaders);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }

    private HttpResponse getResponse(HttpRequest request){
        HttpResponse response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String convert(HttpResponse response){
        String result = "";
        try (InputStream content = response.getContent();) {
            result = CharStreams.toString(new InputStreamReader(content));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void sendToMe() {
        String bizmBody = getBizmBody(new Charger("동길", "01072255198", Template.SEND_TOMORROW_WEEKLY_CHARGER));
        send(bizmBody);
    }
}
