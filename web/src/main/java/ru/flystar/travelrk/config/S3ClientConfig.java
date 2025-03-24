package ru.flystar.travelrk.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3ClientConfig {

    private static final String AWS_KEY_ID = "i5XbomyPMj4TWDvVV2xcvV";
    private static final String AWS_SECRET_KEY = "oMG1vs2Qq3VNP9AVpDgxERzp8z8gFrF6Np24JZzxjFV";

    public static final String VK_CLOUD_STORAGE_URL = "https://hb.vkcloud-storage.ru";

    public static final String RU_MSK = "ru-msk";

    @Bean
    public AmazonS3 amazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(AWS_KEY_ID, AWS_SECRET_KEY);
        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(VK_CLOUD_STORAGE_URL, RU_MSK))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
