package com.wazo.services.formdefinition.dao.impl;

import com.wazo.services.formdefinition.dao.FormDefinitionDAO;
import com.wazo.services.formdefinition.clients.DynamoDb;
import com.wazo.services.formdefinition.dao.entity.FormDefinition;
import com.wazo.services.formdefinition.utils.Constants;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;

import static com.wazo.services.formdefinition.exception.type.ErrorCode.RESOURCE_NOT_FOUND;
import com.wazo.services.formdefinition.exception.type.ResourceNotFoundException;


public class FormDefinitionDAOImpl implements FormDefinitionDAO {
    private final DynamoDb dynamoDb;
    public FormDefinitionDAOImpl(DynamoDb dynamoDb){
        this.dynamoDb=dynamoDb;
    }
    @Override
    public FormDefinition saveFormDefinition(FormDefinition formDefinition) {
        try {
            DynamoDbTable<FormDefinition> tableObj = dynamoDb.getEnhanceClient().table(Constants.TABLE_NAME, TableSchema.fromBean(FormDefinition.class));
            tableObj.putItem(formDefinition);
            return formDefinition;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<FormDefinition> getByOrgIdFormDefinition(String orgId) {
        try{
            DynamoDbTable<FormDefinition> tableObj = dynamoDb.getEnhanceClient().table(Constants.TABLE_NAME, TableSchema.fromBean(FormDefinition.class));

            QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder().partitionValue(orgId).build());
            PageIterable<FormDefinition> results = tableObj.query(queryConditional);

            List<FormDefinition> resultList = new ArrayList<>();
            for (Page<FormDefinition> page : results) {
                resultList.addAll(page.items());
            }

            if(resultList.isEmpty()){
                throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
            }
            return resultList;

        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
            //throw new RuntimeException("Table not found!");
        } catch (DynamoDbException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<FormDefinition> getByOrgIdFormIdFormDefinition(String orgId, String formId) {
        try{
            DynamoDbTable<FormDefinition> tableObj = dynamoDb.getEnhanceClient().table(Constants.TABLE_NAME, TableSchema.fromBean(FormDefinition.class));

            QueryConditional queryConditional = QueryConditional.sortBeginsWith(Key.builder().partitionValue(orgId).sortValue(formId).build());
            PageIterable<FormDefinition> results = tableObj.query(queryConditional);

            List<FormDefinition> resultList = new ArrayList<>();
            for (Page<FormDefinition> page : results) {
                resultList.addAll(page.items());
            }

            if(resultList.isEmpty()){
                throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
            }
            return resultList;

        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
        } catch (DynamoDbException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public FormDefinition getByOrgIdFormIdVersionFormDefinition(String orgId, String formId, String formVersion) {
        try{
            String formIdVersion = formId+"_"+formVersion;
            DynamoDbTable<FormDefinition> tableObj = dynamoDb.getEnhanceClient().table(Constants.TABLE_NAME, TableSchema.fromBean(FormDefinition.class));

            Key key = Key.builder()
                    .partitionValue(orgId)
                    .sortValue(formIdVersion)
                    .build();

            return tableObj.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
            //e.printStackTrace();
            //throw new RuntimeException("Table not found!");
        } catch (DynamoDbException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteFormDefinition(String orgId, String formId, String formVersion) {
        try {
            String formIdVersion = formId+"_"+formVersion;
            DynamoDbTable<FormDefinition> tableObj = dynamoDb.getEnhanceClient().table(Constants.TABLE_NAME, TableSchema.fromBean(FormDefinition.class));

            Key key = Key.builder()
                    .partitionValue(orgId)
                    .sortValue(formIdVersion)
                    .build();

            BatchWriteItemEnhancedRequest request = BatchWriteItemEnhancedRequest.builder()
                    .writeBatches(WriteBatch.builder(FormDefinition.class)
                                    .mappedTableResource(tableObj)
                                    .addDeleteItem(DeleteItemEnhancedRequest.builder()
                                            .key(key)
                                            .build())
                                    .build())
                    .build();

            dynamoDb.getEnhanceClient().batchWriteItem(request);
        } catch (DynamoDbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
