package com.johnny.cs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.io.CharStreams;
import com.johnny.cs.domain.line.request.AccessCodeRequest;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
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

    @Value("${api.accessCode.url}")
    private String accessCodeUrl;


    public void getAccessCode() throws InterruptedException {
        log.info(authUrl);
        System.setProperty("webdriver.chrome.driver", "classpath:chromedriver");
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
    }

    public void getAccessToken(String code) throws IOException {
        HttpRequestFactory requestFactory
                = new NetHttpTransport().createRequestFactory();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("consumerKey", serviceApiConsumerKey);
        httpHeaders.set("Authorization", "Bearer " + code);

        ObjectMapper mapper = new ObjectMapper();
        String params = mapper.writeValueAsString(AccessCodeRequest.getRequest(serviceApiConsumerKey, code));
        HttpRequest request = requestFactory
                .buildPostRequest(new GenericUrl(accessCodeUrl), ByteArrayContent.fromString(null, params))
                .setHeaders(httpHeaders);

        HttpResponse response = request.execute();
        InputStream content = response.getContent();
        String result = CharStreams.toString(new InputStreamReader(content));
        log.info(result);
    }
}
