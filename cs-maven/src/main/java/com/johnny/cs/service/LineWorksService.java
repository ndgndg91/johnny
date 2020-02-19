package com.johnny.cs.service;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.io.CharStreams;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Log4j2
@Service
public class LineWorksService {

    @Value("${api.auth}")
    private String authUrl;
    @Value("${api.host}")
    private String apiHost;
    @Value("${api.apiId}")
    private String apiId;
    @Value("${api.serviceApiConsumerKey}")
    private String serviceApiConsumerKey;
    @Value("${api.firstPath}")
    private String firstPath;
    @Value("${api.secondPath}")
    private String secondPath;
    @Value("${api.clientIdKey}")
    private String clientIdKey;
    @Value("${api.redirectUriKey}")
    private String redirectUriKey;
    @Value("${api.redirectUriValue}")
    private String redirectUriValue;
    @Value("${api.stateKey}")
    private String stateKey;
    @Value("${api.stateValue}")
    private String stateValue;
    @Value("${api.domainKey}")
    private String domainKey;
    @Value("${api.domainValue}")
    private String domainValue;

    public void getAccessCode() throws IOException {
        log.info(authUrl);
        HttpRequestFactory requestFactory
                = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(authUrl));
        HttpResponse httpResponse = request.execute();
        InputStream content = httpResponse.getContent();
        String response = CharStreams.toString(new InputStreamReader(content));

        log.info(response);
    }

    public void getAccessToken(String code) {

    }

//    public void callApi() throws IOException {
//        HttpRequestFactory requestFactory
//                = new NetHttpTransport().createRequestFactory();
//        String url = createUrl();
//        log.info(url);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("consumerKey", SERVER_API_CONSUMER_KEY);
//        httpHeaders.set("Authorization", "Bearer " + ACCESS_TOKEN);
//        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//
//        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url)).setHeaders(httpHeaders);
//        HttpResponse httpResponse = request.execute();
//        InputStream content = httpResponse.getContent();
//        String response = CharStreams.toString(new InputStreamReader(content));
//
//        log.info(response);
//    }
//
//    private String createUrl(){
//       StringBuilder urlBuilder = new StringBuilder(API_HOST)
//               .append("/r/")
//               .append(API_ID)
//               .append("/mail/v2/getFolderList");
//       return urlBuilder.toString();
//    }
}
