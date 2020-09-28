package ru.flystar.travelrk;

import java.util.List;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

/**
 * Project: travelrk
 * Created by Sergej Shestopalov on 06.12.2018.
 */
public class TestDouble {
  private static final String AWS_KEY_ID = "i5XbomyPMj4TWDvVV2xcvV";
  private static final String AWS_SECRET_KEY = "oMG1vs2Qq3VNP9AVpDgxERzp8z8gFrF6Np24JZzxjFV";

  public static void main(String[] args) {
/*        System.out.println(getRaznica(-5.0d, 5.0d));
        System.out.println(getRaznica(5.0d, -5.0d));
        System.out.println(getRaznica(0.0d, -10.0d));
        System.out.println(getRaznica(-10.0d, 0.0d));
        System.out.println(getRaznica(-175.0d, 175.0d));
        System.out.println(getRaznica(175.0d, -175.0d));
        System.out.println(getRaznica(-175.0d, 80.0d));
        System.out.println(getRaznica(80.0d, -175.0d));
        System.out.println(getRaznica(-90.0d, 90.0d));
        System.out.println(getRaznica(90.0d, -90.0d));*/

    AWSCredentials credentials = new BasicAWSCredentials(AWS_KEY_ID, AWS_SECRET_KEY);
    AmazonS3 s3client = AmazonS3ClientBuilder
        .standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(Regions.US_EAST_2)
        .build();
    List<Bucket> buckets = s3client.listBuckets();
    for (Bucket bucket : buckets) {
      System.out.println(bucket.getName());
    }
  }

  private static double getRaznica(double v, double v1) {
    double result = Math.abs(v - v1);
    if (result > 180) result = Math.abs(result - 360);
    return result;
  }
}
