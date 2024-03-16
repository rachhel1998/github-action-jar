package com.wazo.services.formdefinition.activity;

import com.google.gson.Gson;
import com.wazo.services.formdefinition.dao.FormDefinitionDAO;
import com.wazo.services.formdefinition.dao.entity.FormDefinition;
import com.wazo.services.formdefinition.exception.type.ErrorCode;
import com.wazo.services.formdefinition.exception.type.InvalidRequestException;
import com.wazo.services.formdefinition.helper.S3Helper;
import com.wazo.services.formdefinition.model.ApiResponse;
import com.wazo.services.formdefinition.model.CreateFormDefinitionRequest;
import com.wazo.services.formdefinition.utils.Constants;
import com.wazo.services.formdefinition.validation.ValidateCreateFormDefinitionRequest;

import java.time.Instant;
import java.util.UUID;

public class CreateFormDefinitionActivity {
    private final FormDefinitionDAO formDefinitionDAO;
    private final ValidateCreateFormDefinitionRequest validateCreateFormDefinitionRequest;
    private final S3Helper s3Helper;

    private static final Gson gson = new Gson();
    public CreateFormDefinitionActivity(FormDefinitionDAO formDefinitionDAO, ValidateCreateFormDefinitionRequest validateCreateFormDefinitionRequest, S3Helper s3Helper){
        this.formDefinitionDAO = formDefinitionDAO;
        this.validateCreateFormDefinitionRequest = validateCreateFormDefinitionRequest;
        this.s3Helper = s3Helper;
    }
    public ApiResponse run(String orgId, String requestBody){
        CreateFormDefinitionRequest createFormDefinitionRequest = gson.fromJson(requestBody, CreateFormDefinitionRequest.class);
        if(createFormDefinitionRequest==null){
            throw new InvalidRequestException("Invalid request body.", ErrorCode.INVALID_REQUEST);
        }
        if(validateCreateFormDefinitionRequest.isValid(createFormDefinitionRequest)){
            try{
                String formId = UUID.randomUUID().toString();
                String formVersion = String.valueOf(Instant.now().getEpochSecond());
                String formIdVersion = formId+"_"+formVersion;
                String s3ItemKey = orgId+"/FormDefinitions/"+formId+"/"+formVersion+".json";

                s3Helper.putObject(Constants.S3_BUCKET_NAME, s3ItemKey, createFormDefinitionRequest.getFormDefinition().getBytes());

                FormDefinition formDefinition = FormDefinition.builder()
                        .orgId(orgId)
                        .formIdVersion(formIdVersion)
                        .formId(formId)
                        .formVersion(formVersion)
                        .formTitle(createFormDefinitionRequest.getFormTitle())
                        .formDescription(createFormDefinitionRequest.getFormDescription())
                        .formMetaData(createFormDefinitionRequest.getFormMetaData())
                        .formDefinitionS3(s3ItemKey)
                        .stateFlag(createFormDefinitionRequest.getStateFlag())
                        .createdBy(createFormDefinitionRequest.getCreatedBy())
                        .createdAt(Instant.now())
                        .build();

                FormDefinition createFormDefinitionObj = formDefinitionDAO.saveFormDefinition(formDefinition);
                return ApiResponse.builder()
                        .status(201)
                        .message("Form definition creation success!")
                        .data(createFormDefinitionObj)
                        .build();

            }catch(Exception e){
                e.printStackTrace();
                return ApiResponse.builder()
                        .status(200)
                        .message("Something went wrong, try again!")
                        .build();
            }
        }else{
            return ApiResponse.builder()
                    .status(200)
                    .message("Invalid request data!")
                    .errors(validateCreateFormDefinitionRequest.getErrors())
                    .build();
        }
    }
}
