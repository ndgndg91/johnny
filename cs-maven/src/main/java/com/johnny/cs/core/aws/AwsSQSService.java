package com.johnny.cs.core.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsSQSService {

    private static final String EMPTY = "";

    @Value("${aws.test.credential.accessKeyId}")
    private String accessKey;
    @Value("${aws.test.credential.secretKey}")
    private String secretKey;

    private AmazonSQS build() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSStaticCredentialsProvider provider = new AWSStaticCredentialsProvider(credentials);

        AmazonSQSClientBuilder builder = AmazonSQSClientBuilder
            .standard()
            .withRegion(Regions.AP_NORTHEAST_2);

        return builder.withCredentials(provider).build();
    }

    public String getQueueUrl(String queueName) {
        AmazonSQS SQSClient = build();
        try{
            CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
            return SQSClient.createQueue(createQueueRequest).getQueueUrl();
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException, which means your request made it " +
                "to Amazon SQS, but was rejected with an error response for some reason.");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException, which means the client encountered " +
                "a serious internal problem while trying to communicate with SQS, such as not " +
                "being able to access the network.");
            log.info("Error Message: " + ace.getMessage());
        }

        return EMPTY;
    }

    public String sendMessage(String queueName, String message) {
        AmazonSQS SQSClient = build();
        String queueUrl = getQueueUrl(queueName);

        log.info("===========================================");
        log.info("Getting Started with Amazon SQS");
        log.info("===========================================\n");

        String res = null;
        try {
            // Send a message
            log.info("Sending a message to MyQueue.\n");
            SQSClient.sendMessage(new SendMessageRequest(queueUrl, message));
            res = "OK";
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException, which means your request made it " +
                "to Amazon SQS, but was rejected with an error response for some reason.");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException, which means the client encountered " +
                "a serious internal problem while trying to communicate with SQS, such as not " +
                "being able to access the network.");
            log.info("Error Message: " + ace.getMessage());
        }

        return res;
    }
}
