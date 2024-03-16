package com.wazo.services.formdefinition.activity;
import com.wazo.services.formdefinition.dao.FormDefinitionDAO;
import com.wazo.services.formdefinition.dao.entity.FormDefinition;
import com.wazo.services.formdefinition.helper.S3Helper;
import com.wazo.services.formdefinition.model.ApiResponse;
import com.wazo.services.formdefinition.utils.Constants;

public class GetFormDefinitionByOrgIdFormIdVersionActivity {
    private final FormDefinitionDAO formDefinitionDAO;
    private final S3Helper s3Helper;
    public GetFormDefinitionByOrgIdFormIdVersionActivity(FormDefinitionDAO formDefinitionDAO, S3Helper s3Helper){
        this.formDefinitionDAO = formDefinitionDAO;
        this.s3Helper = s3Helper;
    }
    public ApiResponse run(String orgId, String formId, String formVersion){
        try {
            FormDefinition getByIdFormDefinitionObj = formDefinitionDAO.getByOrgIdFormIdVersionFormDefinition(orgId, formId, formVersion);
            String s3Key = getByIdFormDefinitionObj.getFormDefinitionS3();
            getByIdFormDefinitionObj.setFormDefinitionS3(null);
            getByIdFormDefinitionObj.setFormDefinition(new String(s3Helper.getObject(Constants.S3_BUCKET_NAME, s3Key)));

            return ApiResponse.builder()
                    .status(200)
                    .message("Form definition data fetched success!")
                    .data(getByIdFormDefinitionObj)
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