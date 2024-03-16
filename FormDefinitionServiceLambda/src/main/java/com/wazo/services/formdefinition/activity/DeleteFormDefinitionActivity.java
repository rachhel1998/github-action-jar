package com.wazo.services.formdefinition.activity;

import com.wazo.services.formdefinition.dao.FormDefinitionDAO;
import com.wazo.services.formdefinition.helper.S3Helper;
import com.wazo.services.formdefinition.model.ApiResponse;

public class DeleteFormDefinitionActivity {
    private final FormDefinitionDAO formDefinitionDAO;
    private final S3Helper s3Helper;
    public DeleteFormDefinitionActivity(FormDefinitionDAO formDefinitionDAO, S3Helper s3Helper){
        this.formDefinitionDAO = formDefinitionDAO;
        this.s3Helper = s3Helper;
    }

    public ApiResponse run(String orgId, String formId, String formVersion){
        if(orgId!=null && formId!=null){
            formDefinitionDAO.deleteFormDefinition(orgId, formId, formVersion);

            return ApiResponse.builder()
                    .status(200)
                    .message("Form definition delete success!")
                    .build();
        }else{
            return ApiResponse.builder().status(200).message("orgId and formId is required!").build();
        }
    }

}
