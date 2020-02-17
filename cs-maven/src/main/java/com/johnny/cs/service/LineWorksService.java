package com.johnny.cs.service;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.io.CharStreams;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Log4j2
@Service
public class LineWorksService {

    private static final String API_HOST = "https://apis.worksmobile.com";
    private static final String API_ID = "";
    private static final String SERVER_API_CONSUMER_KEY = "";
    private static final String ACCESS_TOKEN = "";

    public void callApi() throws IOException {
        HttpRequestFactory requestFactory
                = new NetHttpTransport().createRequestFactory();
        String url = createUrl();
        log.info(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("consumerKey", SERVER_API_CONSUMER_KEY);
        httpHeaders.set("Authorization", "Bearer " + ACCESS_TOKEN);
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url)).setHeaders(httpHeaders);
        HttpResponse httpResponse = request.execute();
        InputStream content = httpResponse.getContent();
        String response = CharStreams.toString(new InputStreamReader(content));

        log.info(response);
    }

    private String createUrl(){
       StringBuilder urlBuilder = new StringBuilder(API_HOST)
               .append("/r/")
               .append(API_ID)
               .append("/mail/v2/getFolderList");
       return urlBuilder.toString();
    }
}
