package com.wazo.services.formdefinition.validation;

import com.wazo.services.formdefinition.model.CreateFormDefinitionRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateCreateFormDefinitionRequest {
    Map<String, String> validationErrors = new HashMap<>();

    public boolean isValid(CreateFormDefinitionRequest createFormDefinitionRequest){
        boolean validFlag = true;

        if(createFormDefinitionRequest.getFormTitle()==null){
            validFlag = false;
            validationErrors.put("formTitle", "Form Title is required!");
        }

        if(createFormDefinitionRequest.getFormMetaData()==null){
            validFlag = false;
            validationErrors.put("formMetaData", "Form meta data is required");
        }

        if(createFormDefinitionRequest.getFormDefinition()==null){
            validFlag = false;
            validationErrors.put("formDefinition", "Form Definition is required!");
        }

        if(createFormDefinitionRequest.getCreatedBy()==null){
            validFlag = false;
            validationErrors.put("createdBy", "Created By is required!");
        }
        if(createFormDefinitionRequest.getStateFlag()==null){
            validFlag = false;
            validationErrors.put("stateFlag", "State Flag is required!");
        }

        return validFlag;
    }

    public Map<String, String> getErrors(){
        return validationErrors;
    }
}
