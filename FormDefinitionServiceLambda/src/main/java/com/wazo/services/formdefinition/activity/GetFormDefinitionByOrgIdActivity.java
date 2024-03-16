package com.wazo.services.formdefinition.activity;

import com.wazo.services.formdefinition.dao.FormDefinitionDAO;
import com.wazo.services.formdefinition.dao.entity.FormDefinition;
import com.wazo.services.formdefinition.model.ApiResponse;

import java.util.List;

public class GetFormDefinitionByOrgIdActivity {

    private final FormDefinitionDAO formDefinitionDAO;
    public GetFormDefinitionByOrgIdActivity(FormDefinitionDAO formDefinitionDAO){

        this.formDefinitionDAO = formDefinitionDAO;
    }
    public ApiResponse run(String orgId){

        try {
            List<FormDefinition> getAllFormDefinitionObj = formDefinitionDAO.getByOrgIdFormDefinition(orgId);
            return ApiResponse.builder()
                    .status(200)
                    .message("Form definition data fetched success!")
                    .data(getAllFormDefinitionObj)
                    .build();

        }catch(Exception e){
            e.printStackTrace();
            return ApiResponse.builder()
                    .status(200)
                    .message("Something went wrong, try again! GetAllActivity "+e.getMessage())
                    .build();
        }

    }
}
