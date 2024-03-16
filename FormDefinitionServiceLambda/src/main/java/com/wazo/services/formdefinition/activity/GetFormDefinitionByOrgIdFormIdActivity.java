package com.wazo.services.formdefinition.activity;

import com.wazo.services.formdefinition.dao.FormDefinitionDAO;
import com.wazo.services.formdefinition.dao.entity.FormDefinition;
import com.wazo.services.formdefinition.model.ApiResponse;

import java.util.List;

public class GetFormDefinitionByOrgIdFormIdActivity {
    private final FormDefinitionDAO formDefinitionDAO;
    public GetFormDefinitionByOrgIdFormIdActivity(FormDefinitionDAO formDefinitionDAO){

        this.formDefinitionDAO = formDefinitionDAO;
    }
    public ApiResponse run(String orgId, String formId){
        try {
            List<FormDefinition> getAllFormDefinitionObj = formDefinitionDAO.getByOrgIdFormIdFormDefinition(orgId, formId);
            return ApiResponse.builder()
                    .status(200)
                    .message("Form definition versions fetched success!")
                    .data(getAllFormDefinitionObj)
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
