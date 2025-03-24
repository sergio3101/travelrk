package ru.flystar.travelrk.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.flystar.travelrk.ExclusiveTourModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log4j
public class AwsExclusiveTourService {
    public static final String STATIC_URL = "http://static.travelrk.ru/";
    public static final String BACKET_NAME = "travelrk_static";
    public static final String PREFIX = "3d";
    public static final String DELIMITER = "/";

    @Autowired
    private AmazonS3 s3Client;

    public List<ExclusiveTourModel> getAllExclusiveTours() {
        List<String> listOf3D = listKeysInDirectory();
        return listOf3D.stream()
                .filter(s -> s.contains("/"))
                .map(this::buildKey)
                .map(this::returnIfExist)
                .filter(Objects::nonNull)
                .map(this::buildDto)
                .collect(Collectors.toList());

    }

    private String buildKey(String path) {
        return path + Arrays.asList(path.split("/")).get(1) + ".html";
    }

    private S3Object returnIfExist(String key) {
        try {
            return s3Client.getObject(BACKET_NAME, key);
        } catch (AmazonS3Exception e) {
            return null;
        }
    }

    private ExclusiveTourModel buildDto(S3Object object) {
        try {
            S3ObjectInputStream ois = object.getObjectContent();
            String string = IOUtils.toString(ois, StandardCharsets.UTF_8);
            String title = Jsoup.parse(string).select("html").select("head").select("title").get(0).text();
            String imageUrl = Jsoup.parse(string).select("html").select("head").select("link").stream()
                    .filter(element -> element.hasAttr("rel"))
                    .filter(element -> element.attr("rel").contains("image_src"))
                    .findFirst().get().attr("href");
            List<String> objectKeyParts = Arrays.asList(object.getKey().split("/"));
            return ExclusiveTourModel.builder()
                    .path(STATIC_URL + object.getKey())
                    .logo(STATIC_URL + objectKeyParts.get(0) + "/" +  objectKeyParts.get(1) + "/" + imageUrl)
                    .name(title)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> listKeysInDirectory() {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(BACKET_NAME).withPrefix(PREFIX + DELIMITER)
                .withDelimiter(DELIMITER);
        ObjectListing objects = s3Client.listObjects(listObjectsRequest);
        return objects.getCommonPrefixes();
    }
}
