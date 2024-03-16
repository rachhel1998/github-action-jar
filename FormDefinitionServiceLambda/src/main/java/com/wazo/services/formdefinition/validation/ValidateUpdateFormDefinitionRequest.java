package com.wazo.services.formdefinition.validation;

import com.wazo.services.formdefinition.model.UpdateFormDefinitionRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateUpdateFormDefinitionRequest {
    Map<String, String> validationErrors = new HashMap<>();

    public boolean isValid(UpdateFormDefinitionRequest updateFormDefinitionRequest){
        boolean validFlag = true;
        if(updateFormDefinitionRequest.getStateFlag()==null){
            validFlag = false;
            validationErrors.put("stateFlag", "State flag is required");
        }
        return validFlag;
    }

    public Map<String, String> getErrors(){
        return validationErrors;
    }
}