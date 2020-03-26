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
import com.johnny.cs.core.domain.person.HoBot;
import com.johnny.cs.core.domain.person.HolidayCharger;
import com.johnny.cs.core.domain.person.today.TodayHolidayCharger;
import com.johnny.cs.core.domain.person.today.TodayNighttimeCharger;
import com.johnny.cs.core.domain.person.today.TodayWeeklyCharger;
import com.johnny.cs.core.domain.person.tomorrow.TomorrowCharger;
import com.johnny.cs.core.util.JacksonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import com.johnny.cs.core.util.PhoneUtils;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@EqualsAndHashCode
@Slf4j
@Service
public class AlarmService {

    private static final String IMMEDIATELY = "00000000000000";
    private static final String APPLICATION_JSON = "application/json";

    @Value("${kakao.profileKey}")
    private String profileKey;

    @Value("${kakao.endPoint}")
    private String endPoint;

    @Value("${kakao.bizmUserId}")
    private String bizmUserId;

    @Value("${kakao.bizmUserKey}")
    private String bizmUserKey;

    @Value("${kakao.phone}")
    private String sendPhone;

    public void sendAlarm(TodayHolidayCharger todayHolidayCharger) {
        sendAlarm(todayHolidayCharger.getHolidayCharger());
        log.info("오늘 휴일 담당자에게 알람 발신 완료!");
    }

    public void sendAlarm(TodayNighttimeCharger todayNighttimeCharger) {
        sendAlarm(todayNighttimeCharger.getNighttimeCharger());
        log.info("오늘 야간 담당자에게 알람 발신 완료!");
    }

    public void sendAlarm(TodayWeeklyCharger todayWeeklyCharger) {
        sendAlarms(todayWeeklyCharger.getWeeklyChargers());
        log.info("오늘 주간 담당자들에게 알람 발신 완료!");
    }

    public void sendAlarm(TomorrowCharger tomorrowChargers) {
        if (tomorrowChargers.isTomorrowHoliday()) {
            HolidayCharger holidayCharger = tomorrowChargers.getHolidayCharger();
            sendAlarm(holidayCharger);
            return;
        }

        List<Charger> weekDayChargers = tomorrowChargers.getWeekDayChargers();
        sendAlarms(weekDayChargers);
        log.info("내일 담당자들에게 알람 발신 완료!");
    }

    public void sendAlarm(Charger charger) {
        String bizmBody = getBizmBody(charger);
        log.info(bizmBody);
        send(bizmBody);
    }

    public <C extends Charger> void sendAlarms(List<C> chargers) {
        for (Charger charger : chargers) {
            String bizmBody = getBizmBody(charger);
            log.info(bizmBody);
            send(bizmBody);
        }
    }

    private String getBizmBody(Charger charger) {
        return getBizmBody(charger.getName(),
                charger.getTemplate().getTemplateCode(),
                charger.getPhone(),
                charger.getTemplate().getPattern());
    }

    private String getBizmBody(String name, String templateCode, String phone, String pattern) {
        Bizm bizm = Bizm.builder()
                .sendPhone(sendPhone)
                .name(name)
                .msgId(BizmUtils.generateKey())
                .profileKey(profileKey)
                .templateCode(templateCode)
                .receiverNum(phone)
                .message(pattern)
                .reservedTime(IMMEDIATELY)
                .build();
        bizm.reviseChargerName();
        return "[" + JacksonUtils.toJson(bizm) + "]";
    }

    public void sendToHoBot(Charger charger) {
        HoBot hoBot = HoBot.getInstance();
        String bizmBody = getBizmBody(hoBot, charger);
        log.info(bizmBody);
        send(bizmBody);
    }

    private String getBizmBody(HoBot hoBot, Charger charger) {
        return getBizmBody(charger.getName(),
                hoBot.getTemplate().getTemplateCode(),
                hoBot.getPhone(),
                hoBot.getTemplate().getPattern());
    }

    private void send(String body) {
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(APPLICATION_JSON);
        httpHeaders.setAccept(APPLICATION_JSON);
        httpHeaders.set("userid", bizmUserId);

        HttpRequest request = Objects.requireNonNull(createRequest(requestFactory, httpHeaders, body));
        HttpResponse response = Objects.requireNonNull(getResponse(request));

        String result = convert(response);
        log.info(result);
    }

    private HttpRequest createRequest(HttpRequestFactory requestFactory, HttpHeaders httpHeaders, String body) {
        HttpRequest request = null;
        try {
            request = requestFactory.buildPostRequest(new GenericUrl(endPoint), ByteArrayContent
                    .fromString(APPLICATION_JSON, body)).setHeaders(httpHeaders);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }

    private HttpResponse getResponse(HttpRequest request) {
        HttpResponse response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String convert(HttpResponse response) {
        String result = "";
        try (InputStream content = response.getContent();) {
            result = CharStreams.toString(new InputStreamReader(content));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void sendToMe() {
        String bizmBody = getBizmBody(new Charger("동길", PhoneUtils.getPhoneBook().get("동길"), Template.SEND_TO_HO_BOT));
        log.info(bizmBody);
        send(bizmBody);
    }
}
