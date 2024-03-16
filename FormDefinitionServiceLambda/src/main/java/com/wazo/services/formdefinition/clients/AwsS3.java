package com.wazo.services.formdefinition.clients;


import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class AwsS3 {
    public S3Client getClient(){
        try {
            return S3Client.builder()
                    .region(Region.US_EAST_1)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
