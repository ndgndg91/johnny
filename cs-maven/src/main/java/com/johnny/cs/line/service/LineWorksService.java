package com.johnny.cs.line.service;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.io.CharStreams;
import com.johnny.cs.line.domain.request.AccessTokenRequest;
import com.johnny.cs.line.domain.response.AccessCodeResponse;
import com.johnny.cs.line.domain.response.mailfolder.MailData;
import com.johnny.cs.line.domain.response.mailfolder.MailListResponse;
import com.johnny.cs.core.util.JacksonUtils;
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
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
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

    @Value("${api.readMail.url}")
    private String readMailUrl;

    @Value("${google.chrome.driver.path}")
    private String chromeDriverPath;

    public void getAccessCode() throws InterruptedException {
        log.info(authUrl);
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver = new ChromeDriver();
        Thread.sleep(500);
        driver.get(authUrl);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        if (executor.executeScript("return document.readyState").equals("complete")) {
            log.info("완료 했엉!");
            WebElement inputId = driver.findElement(By.id("inputId"));
            inputId.clear();
            inputId.sendKeys(helpId);
            WebElement inputPassword = driver.findElement(By.id("password"));
            inputPassword.clear();
            inputPassword.sendKeys(helpPassword);
            Thread.sleep(1000);
            WebElement btn = driver.findElement(By.className("btn"));
            btn.click();
        }
        driver.close();
        log.info("이유가 뭐지?");
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

    public void testApi(String accessToken) throws IOException {
        log.info(accessToken);
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        String url = createUrl();
        HttpHeaders httpHeaders = getHeaders(accessToken);
        List<MailData> mails = getMailData(requestFactory, url, httpHeaders);

        for (MailData mail : mails) {
//            mail.getMailSN()
        }
    }

    private List<MailData> getMailData(HttpRequestFactory requestFactory, String url, HttpHeaders httpHeaders) throws IOException {
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url)).setHeaders(httpHeaders);
        HttpResponse response = request.execute();
        try(InputStream content = response.getContent()){
            String result = CharStreams.toString(new InputStreamReader(content));
            log.info(result);
            MailListResponse mails = JacksonUtils.getMapper().readValue(result, MailListResponse.class);
            log.info("{}", mails);
            return mails.getMailData();
        }
    }

    private HttpHeaders getHeaders(String accessToken){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("consumerKey", serviceApiConsumerKey);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        return httpHeaders;
    }

    private String createUrl(){
        StringBuilder urlBuilder = new StringBuilder(mailListUrl);
        urlBuilder.append("?").append(URLEncoder.encode("folderSN", UTF_8)).append("=").append(receiveFolder);
        urlBuilder.append("&").append(URLEncoder.encode("sortField", UTF_8)).append("=").append(sortStandard);
        urlBuilder.append("&").append(URLEncoder.encode("pageSize", UTF_8)).append("=").append(URLEncoder.encode("10", UTF_8));
        return urlBuilder.toString();
    }
}
