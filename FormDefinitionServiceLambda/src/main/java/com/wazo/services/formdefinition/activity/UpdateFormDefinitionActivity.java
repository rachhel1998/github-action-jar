package com.wazo.services.formdefinition.activity;

import com.google.gson.Gson;
import com.wazo.services.formdefinition.dao.FormDefinitionDAO;
import com.wazo.services.formdefinition.dao.entity.FormDefinition;
import com.wazo.services.formdefinition.helper.S3Helper;
import com.wazo.services.formdefinition.model.ApiResponse;
import com.wazo.services.formdefinition.model.UpdateFormDefinitionRequest;
import com.wazo.services.formdefinition.utils.Constants;
import com.wazo.services.formdefinition.validation.ValidateUpdateFormDefinitionRequest;

import java.time.Instant;

public class UpdateFormDefinitionActivity {
    private final FormDefinitionDAO formDefinitionDAO;
    private final ValidateUpdateFormDefinitionRequest validateUpdateFormDefinitionRequest;
    private final S3Helper s3Helper;
    private static final Gson gson = new Gson();
    public UpdateFormDefinitionActivity(FormDefinitionDAO formDefinitionDAO, ValidateUpdateFormDefinitionRequest validateUpdateFormDefinitionRequest, S3Helper s3Helper){
        this.formDefinitionDAO = formDefinitionDAO;
        this.validateUpdateFormDefinitionRequest = validateUpdateFormDefinitionRequest;
        this.s3Helper = s3Helper;
    }
    public ApiResponse run(String orgId, String formId, String formVersion, String requestBody){

        UpdateFormDefinitionRequest updateFormDefinitionRequest = gson.fromJson(requestBody, UpdateFormDefinitionRequest.class);

        if(validateUpdateFormDefinitionRequest.isValid(updateFormDefinitionRequest)) {
            try{
                String newFormVersion = String.valueOf(Instant.now().getEpochSecond());
                String s3ItemKey = orgId+"/FormDefinitions/"+formId+"/"+newFormVersion+".json";
                if (updateFormDefinitionRequest.getFormDefinition()!=null){
                    s3Helper.putObject(Constants.S3_BUCKET_NAME, s3ItemKey, updateFormDefinitionRequest.getFormDefinition().getBytes());
                }else {
                    s3ItemKey=null;
                }

                String formIdVersion = formId.concat("_").concat(newFormVersion);
                FormDefinition formDefinition = FormDefinition.builder()
                        .orgId(orgId)
                        .formIdVersion(formIdVersion)
                        .formId(formId)
                        .formVersion(newFormVersion)
                        .formTitle(updateFormDefinitionRequest.getFormTitle())
                        .formDescription(updateFormDefinitionRequest.getFormDescription())
                        .formMetaData(updateFormDefinitionRequest.getFormMetaData())
                        .formDefinitionS3(s3ItemKey)
                        .stateFlag(updateFormDefinitionRequest.getStateFlag())
                        .createdBy(updateFormDefinitionRequest.getCreatedBy())
                        .createdAt(Instant.now())
                        .build();

                FormDefinition createFormDefinitionObj = formDefinitionDAO.saveFormDefinition(formDefinition);
                return ApiResponse.builder()
                        .status(200)
                        .message("Form definition update success!")
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
                    .errors(validateUpdateFormDefinitionRequest.getErrors())
                    .build();
        }
    }
}
