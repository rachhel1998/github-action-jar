package com.wazo.services.formdefinition.helper;

import com.wazo.services.formdefinition.clients.AwsS3;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
public class S3Helper {

    private final AwsS3 awsS3;
    public S3Helper(AwsS3 awsS3){
        this.awsS3=awsS3;
    }

    public boolean putObject(String bucketName, String key, byte[] data){
        try {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            awsS3.getClient().putObject(objectRequest, RequestBody.fromBytes(data));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public byte[] getObject(String bucketName, String key){
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            ResponseInputStream<GetObjectResponse> res = awsS3.getClient().getObject(getObjectRequest);
            return res.readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean deleteObject(String bucketName, String key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            awsS3.getClient().deleteObject(deleteObjectRequest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
