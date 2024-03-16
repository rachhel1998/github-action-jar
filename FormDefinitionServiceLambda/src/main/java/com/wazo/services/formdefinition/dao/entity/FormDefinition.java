package com.wazo.services.formdefinition.dao.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@DynamoDbBean
public class FormDefinition {
    private String orgId;
    private String formIdVersion;
    private String formId;
    private String formVersion;
    private String formTitle;
    private String formDescription;
    private String formMetaData;
    private String formDefinitionS3;
    private String formDefinition;
    private String stateFlag;
    private Instant createdAt;
    private String createdBy;
    public FormDefinition(){}
    @DynamoDbPartitionKey
    public String getOrgId() {
        return orgId;
    }
    @DynamoDbSortKey
    public String getFormIdVersion() {
        return formIdVersion;
    }

}

