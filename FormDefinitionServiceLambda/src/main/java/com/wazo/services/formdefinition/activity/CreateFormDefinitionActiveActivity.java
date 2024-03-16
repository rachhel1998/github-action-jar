package com.wazo.services.formdefinition.activity;

import com.google.gson.Gson;
import com.wazo.services.formdefinition.dao.FormDefinitionDAO;
import com.wazo.services.formdefinition.dao.entity.FormDefinition;
import com.wazo.services.formdefinition.model.ApiResponse;

import java.time.Instant;

public class CreateFormDefinitionActiveActivity {
    private final FormDefinitionDAO formDefinitionDAO;
    public CreateFormDefinitionActiveActivity(FormDefinitionDAO formDefinitionDAO){
        this.formDefinitionDAO = formDefinitionDAO;
    }
    public ApiResponse run(String orgId, String formId, String formVersion){
        try{
            FormDefinition prevFormDefinitionObj = formDefinitionDAO.getByOrgIdFormIdVersionFormDefinition(orgId, formId, formVersion);
            String activeVersion = "active";
            String formIdActiveVersion = formId.concat("_").concat(activeVersion);
            FormDefinition formDefinitionActive = FormDefinition.builder()
                    .orgId(orgId)
                    .formIdVersion(formIdActiveVersion)
                    .formId(formId)
                    .formVersion(activeVersion)
                    .formTitle(prevFormDefinitionObj.getFormTitle())
                    .formDescription(prevFormDefinitionObj.getFormDescription())
                    .formMetaData(prevFormDefinitionObj.getFormMetaData())
                    .formDefinitionS3(prevFormDefinitionObj.getFormDefinitionS3())
                    .stateFlag(prevFormDefinitionObj.getStateFlag())
                    .createdBy(prevFormDefinitionObj.getCreatedBy())
                    .createdAt(Instant.now())
                    .build();
            FormDefinition createFormDefinitionActiveObj = formDefinitionDAO.saveFormDefinition(formDefinitionActive);

            return ApiResponse.builder()
                    .status(200)
                    .message("Form definition activation success!")
                    .data(createFormDefinitionActiveObj)
                    .build();



        }catch(Exception e){
            e.printStackTrace();
            return ApiResponse.builder()
                    .status(200)
                    .message("Something went wrong, try again!")
                    .build();
        }

    }
}
