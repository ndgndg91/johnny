package com.johnny.cs.date.service;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.io.CharStreams;
import com.johnny.cs.date.domain.response.HolidayResponse;
import com.johnny.cs.date.util.LocalDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class HolidayService {

    @Value("${open.api.key}")
    private String apiKey;

    @Value("${open.api.endpoint}")
    private String endPoint;

    public boolean isHoliday(LocalDate target) {
        //올해의 임시 공휴일 혹은 식스샵 자체 공휴일의 경우
        if (LocalDateUtils.isTempHolidayIn2020(target)) {
            return true;
        }

        if (isWeekEnd(target)) {
            return true;
        }

        HttpResponse response = Objects.requireNonNull(getHttpResponse(target), "공공데이터 api 통신 장애 발생");
        String xml = getResponse(response);
        log.info(xml);

        JAXBContext jaxbContext = Objects.requireNonNull(getContext(), "JAXBContext 실패");
        Unmarshaller unmarshaller = Objects.requireNonNull(getUnmarshaller(jaxbContext), "Unmarshaller 생성 실패");

        HolidayResponse holidays = Objects.requireNonNull(parseXml(xml, unmarshaller), "Xml parse 실패");

        return isHoliday(target, holidays);
    }

    private boolean isWeekEnd(LocalDate target){
        DayOfWeek day = DayOfWeek.of(target.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private JAXBContext getContext(){
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(HolidayResponse.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return context;
    }

    private Unmarshaller getUnmarshaller(JAXBContext jaxbContext) {
        Unmarshaller unmarshaller = null;
        try {
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return unmarshaller;
    }

    private  HolidayResponse parseXml(String xml, Unmarshaller unmarshaller) {
        HolidayResponse response = null;
        try (StringReader reader = new StringReader(xml)) {
            response = (HolidayResponse) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return response;
    }


    private HttpResponse getHttpResponse(LocalDate target){
        HttpRequestFactory requestFactory
                = new NetHttpTransport().createRequestFactory();
        String url = createUrl(target);
        HttpHeaders httpHeaders = createHeaders();
        HttpRequest request;
        HttpResponse response = null;
        try {
            request = requestFactory.buildGetRequest(new GenericUrl(url)).setHeaders(httpHeaders);
            response = request.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getResponse(HttpResponse rawResponse){
        String response = "";
        try (InputStream content = rawResponse.getContent();) {
            response = CharStreams.toString(new InputStreamReader(content));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        return httpHeaders;
    }

    private String createUrl(LocalDate target) {
        StringBuilder urlBuilder = new StringBuilder(endPoint); /*URL*/
        urlBuilder.append("?").append(URLEncoder.encode("ServiceKey", UTF_8)).append("=").append(apiKey); /*Service Key*/
        urlBuilder.append("&").append(URLEncoder.encode("solYear", UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(target.getYear()), UTF_8)); /*연*/
        urlBuilder.append("&").append(URLEncoder.encode("solMonth", UTF_8)).append("=").append(URLEncoder.encode(getMonthString(target), UTF_8)); /*월*/
        return urlBuilder.toString();
    }

    private String getMonthString(LocalDate target){
        String monthString = String.valueOf(target.getMonth().getValue());
        return monthString.length() == 1 ? "0"+ target.getMonth().getValue() : monthString;
    }

    private boolean isHoliday(LocalDate target, HolidayResponse response) {
        if (Objects.isNull(response.getBody().getItems().getItems())) {
            return false;
        }

        return response.getBody().getItems().getItems().stream().anyMatch(item -> {
            LocalDate parsed = LocalDate.parse(item.getLocdate(), DateTimeFormatter.ofPattern("uuuuMMdd"));
            return parsed.equals(target);
        });
    }
}
