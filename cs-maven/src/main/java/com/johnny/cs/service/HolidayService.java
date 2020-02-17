package com.johnny.cs.service;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.io.CharStreams;
import com.johnny.cs.domain.HolidayResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import lombok.extern.log4j.Log4j2;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class HolidayService {

    @Value("${open.api.key}")
    private String apiKey;

    @Value("${open.api.endpoint}")
    private String endPoint;

    public boolean isHoliday(LocalDate target) throws IOException, JAXBException {
        HttpRequestFactory requestFactory
            = new NetHttpTransport().createRequestFactory();
        String url = createUrl(target);
        log.info(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(CONTENT_TYPE, ContentType.APPLICATION_JSON);
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url)).setHeaders(httpHeaders);
        HttpResponse rawResponse = request.execute();
        InputStream content = rawResponse.getContent();
        String response = CharStreams.toString(new InputStreamReader(content));

        log.info(response);
        JAXBContext jaxbContext = JAXBContext.newInstance(HolidayResponse.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(response);
        HolidayResponse holidays = (HolidayResponse) unmarshaller.unmarshal(reader);
        log.info("{}", holidays);
        return isHoliday(target, holidays);
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
        return response.getBody().getItems().getItems().stream().anyMatch(item -> {
            LocalDate parsed = LocalDate.parse(item.getLocdate(), DateTimeFormatter.ofPattern("uuuuMMdd"));
            return parsed.equals(target);
        });
    }
}
