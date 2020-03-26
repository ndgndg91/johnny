package com.johnny.cs.line.service;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.io.CharStreams;
import com.johnny.cs.alarm.domain.Template;
import com.johnny.cs.alarm.service.AlarmService;
import com.johnny.cs.core.domain.person.Charger;
import com.johnny.cs.core.domain.person.HolidayCharger;
import com.johnny.cs.core.domain.person.today.TodayHolidayCharger;
import com.johnny.cs.core.domain.person.today.TodayNighttimeCharger;
import com.johnny.cs.core.util.JacksonUtils;
import com.johnny.cs.date.service.HolidayService;
import com.johnny.cs.line.domain.request.AccessTokenRequest;
import com.johnny.cs.line.domain.response.AccessCodeResponse;
import com.johnny.cs.line.domain.response.mailfolder.MailData;
import com.johnny.cs.line.domain.response.mailfolder.MailListResponse;
import com.johnny.cs.line.domain.response.mailread.MailInfo;
import com.johnny.cs.line.domain.response.mailread.ReadMailResponse;
import com.johnny.cs.spreadsheet.service.SpreadSheetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
@RequiredArgsConstructor
public class LineWorksService {

    @Value("${api.auth.url}")
    private String authUrl;
    @Value("${api.auth.host}")
    private String apiHost;
    @Value("${api.auth.apiId}")
    private String apiId;
    @Value("${api.auth.serviceApiConsumerKey}")
    private String serviceApiConsumerKey;

    @Value("${help.id}")
    private String helpId;
    @Value("${help.password}")
    private String helpPassword;

    @Value("${api.accessToken.url}")
    private String accessTokenUrl;

    @Value("${api.mailList.url}")
    private String mailListUrl;
    @Value("${api.mailList.folderSN}")
    private String receiveFolder;
    @Value("${api.mailList.sortField}")
    private String sortStandard;

    private static final String PAGE_SIZE = "5";

    @Value("${api.readMail.url}")
    private String readMailUrl;

    @Value("${google.chrome.driver.path}")
    private String chromeDriverPath;

    private final AlarmService alarmService;

    private final SpreadSheetsService spreadSheetsService;

    private final HolidayService holidayService;

    public void unRepliedMailCheck() {
        log.info(authUrl);
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = new ChromeDriver();
        driver.get(authUrl);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        if (executor.executeScript("return document.readyState").equals("complete")) {
            WebElement inputId = driver.findElement(By.id("inputId"));
            inputId.clear();
            inputId.sendKeys(helpId);
            WebElement inputPassword = driver.findElement(By.id("password"));
            inputPassword.clear();
            inputPassword.sendKeys(helpPassword);
            WebElement btn = driver.findElement(By.className("btn"));
            btn.click();
            log.info("로그인 버튼 클릭!");
        }
        driver.close();
        log.info("크롬 드라이버 일은 완료 했엉!");
    }

    public AccessCodeResponse getAccessToken(String code) throws IOException {
        HttpRequestFactory requestFactory
                = new NetHttpTransport().createRequestFactory();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("consumerKey", serviceApiConsumerKey);
        httpHeaders.set("Authorization", "Bearer " + code);

        AccessTokenRequest accessTokenRequest = AccessTokenRequest.getRequest(serviceApiConsumerKey, code);
        String url = accessTokenRequest.addQueryString(accessTokenUrl);
        HttpRequest request = requestFactory
                .buildGetRequest(new GenericUrl(url))
                .setHeaders(httpHeaders);

        HttpResponse response = request.execute();
        InputStream content = response.getContent();
        String result = CharStreams.toString(new InputStreamReader(content));
        log.info(result);
        return JacksonUtils.getMapper().readValue(result, AccessCodeResponse.class);
    }

    public Set<MailInfo> getUnRepliedMails(String accessToken) throws IOException {
        log.info(accessToken);
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        String url = createGetFolderUrl();
        HttpHeaders httpHeaders = getHeaders(accessToken);
        List<MailData> mails = getMailData(requestFactory, url, httpHeaders);

        Set<MailInfo> unRepliedMails = new HashSet<>();
        for (MailData mail : mails) {
            String getMailUrl = createGetMailUrl(mail.getMailSN());
            ReadMailResponse mailResponse = getReadMail(requestFactory, getMailUrl, httpHeaders);
            if (mailResponse.getMailInfo().hasNotSendReplyMail()) {
                unRepliedMails.add(mailResponse.getMailInfo());
            }
        }

        return unRepliedMails;
    }

    private List<MailData> getMailData(HttpRequestFactory requestFactory, String url, HttpHeaders httpHeaders) throws IOException {
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url)).setHeaders(httpHeaders);
        HttpResponse response = request.execute();
        try(InputStream content = response.getContent()){
            String result = CharStreams.toString(new InputStreamReader(content));
            MailListResponse mails = JacksonUtils.getMapper().readValue(result, MailListResponse.class);
            return mails.getMailData();
        }
    }

    private ReadMailResponse getReadMail(HttpRequestFactory requestFactory, String url, HttpHeaders httpHeaders) throws IOException {
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url)).setHeaders(httpHeaders);
        HttpResponse response = request.execute();
        try (InputStream content = response.getContent()) {
            String result = CharStreams.toString(new InputStreamReader(content));
            return JacksonUtils.getMapper().readValue(result, ReadMailResponse.class);
        }
    }

    private HttpHeaders getHeaders(String accessToken){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("consumerKey", serviceApiConsumerKey);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        return httpHeaders;
    }

    private String createGetFolderUrl(){
        StringBuilder urlBuilder = new StringBuilder(mailListUrl);
        urlBuilder.append("?").append(URLEncoder.encode("folderSN", UTF_8)).append("=").append(receiveFolder);
        urlBuilder.append("&").append(URLEncoder.encode("sortField", UTF_8)).append("=").append(sortStandard);
        urlBuilder.append("&").append(URLEncoder.encode("pageSize", UTF_8)).append("=").append(PAGE_SIZE);
        return urlBuilder.toString();
    }

    private String createGetMailUrl(long mailSN){
        StringBuilder urlBuilder = new StringBuilder(readMailUrl);
        urlBuilder.append("?").append(URLEncoder.encode("mailSN", UTF_8))
                .append("=")
                .append(URLEncoder.encode(String.valueOf(mailSN), UTF_8));
        urlBuilder.append("&").append(URLEncoder.encode("threadMail", UTF_8)).append("=").append("true");
        return urlBuilder.toString();
    }

    public void checkUnRepliedMailDuration(Set<MailInfo> unRepliedMails) {
        if (unRepliedMails.isEmpty()) {
            return;
        }

        Set<MailInfo> lateMails = unRepliedMails.stream()
                .filter(MailInfo::isUnRepliedForOneHour)
                .collect(Collectors.toUnmodifiableSet());

        if (!lateMails.isEmpty()) {
            log.info("{}", lateMails);
            getLateCharger().ifPresent(c -> {
                alarmService.sendAlarm(c);
                alarmService.sendToHoBot(c);
            });
            log.info("지각 메일 {} 통 있음, 알림 전송 완료!", lateMails.size());
            return;
        }

        Set<MailInfo> rushToReplyMails = unRepliedMails.stream()
                .filter(MailInfo::isUnRepliedForThirtyMinutes)
                .collect(Collectors.toUnmodifiableSet());

        if (!rushToReplyMails.isEmpty()) {
            log.info("{}", rushToReplyMails);
            getRushCharger().ifPresent(c -> {
                alarmService.sendAlarm(c);
                alarmService.sendToHoBot(c);
            });
            log.info("30분 답장 안 한 메일 {} 통 있음, 알림 전송 완료!", rushToReplyMails.size());
        }
    }

    private Optional<Charger> getLateCharger(){
        boolean todayIsHoliday = holidayService.isHoliday(LocalDate.now());
        boolean todayIsWeekDay = !todayIsHoliday;
        Charger lateCharger = null;

        if (todayIsHoliday) {
            TodayHolidayCharger todayHolidayCharger = spreadSheetsService.getTodayHolidayCharger(Template.SEND_UN_REPLIED_MAIL_1H_CHARGER);
            lateCharger = todayHolidayCharger.getHolidayCharger();
        }

        if (todayIsWeekDay) {
            TodayNighttimeCharger todayNighttimeChargers = spreadSheetsService.getTodayNighttimeChargers(Template.SEND_UN_REPLIED_MAIL_1H_CHARGER);
            lateCharger = todayNighttimeChargers.getNighttimeCharger();
        }

        return Optional.ofNullable(lateCharger);
    }

    private Optional<Charger> getRushCharger(){
        boolean todayIsHoliday = holidayService.isHoliday(LocalDate.now());
        boolean todayIsWeekDay = !todayIsHoliday;
        Charger lateCharger = null;

        if (todayIsHoliday) {
            TodayHolidayCharger todayHolidayCharger = spreadSheetsService.getTodayHolidayCharger(Template.SEND_UN_REPLIED_MAIL_30M_CHARGER);
            lateCharger = todayHolidayCharger.getHolidayCharger();
        }

        if (todayIsWeekDay) {
            TodayNighttimeCharger todayNighttimeChargers = spreadSheetsService.getTodayNighttimeChargers(Template.SEND_UN_REPLIED_MAIL_30M_CHARGER);
            lateCharger = todayNighttimeChargers.getNighttimeCharger();
        }

        return Optional.ofNullable(lateCharger);
    }
}
