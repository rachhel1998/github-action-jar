package com.wazo.services.formdefinition.clients;


import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDb {
    public DynamoDbClient getClient(){
        try {
            return DynamoDbClient.builder()
                    .region(Region.US_EAST_1)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public DynamoDbEnhancedClient getEnhanceClient(){
        try {
            return DynamoDbEnhancedClient.builder()
                    .dynamoDbClient(DynamoDbClient.builder()
                            .region(Region.US_EAST_1)
                            .build())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
