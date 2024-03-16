package com.wazo.services.formdefinition.dagger;

import com.wazo.services.formdefinition.activity.*;
import com.wazo.services.formdefinition.clients.AwsS3;
import com.wazo.services.formdefinition.clients.DynamoDb;
import com.wazo.services.formdefinition.dao.FormDefinitionDAO;
import com.wazo.services.formdefinition.dao.impl.FormDefinitionDAOImpl;
import com.wazo.services.formdefinition.helper.S3Helper;
import com.wazo.services.formdefinition.validation.ValidateCreateFormDefinitionRequest;
import com.wazo.services.formdefinition.validation.ValidateUpdateFormDefinitionRequest;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;



@Module()
public class AppModule {

    @Provides
    @Singleton
    public DynamoDb provideDynamoDb(){
        return new DynamoDb();
    }

    @Provides
    @Singleton
    public AwsS3 provideAwsS3(){
        return new AwsS3();
    }

    @Provides
    @Singleton
    public S3Helper privideS3Helper(AwsS3 awsS3){
        return new S3Helper(awsS3);
    }

    @Provides
    @Singleton
    public FormDefinitionDAO provideFormDefinitionDAO(DynamoDb dynamoDb){
        return new FormDefinitionDAOImpl(dynamoDb);
    }

    @Provides
    @Singleton
    public ValidateCreateFormDefinitionRequest provideValidateCreateFormDefinitionRequest(){
        return new ValidateCreateFormDefinitionRequest();
    }

    @Provides
    @Singleton
    public CreateFormDefinitionActivity provideCreateFormDefinitionActivity(FormDefinitionDAO formDefinitionDAO, ValidateCreateFormDefinitionRequest validateCreateFormDefinitionRequest, S3Helper s3Helper){
        return new CreateFormDefinitionActivity(formDefinitionDAO, validateCreateFormDefinitionRequest, s3Helper);
    }


    @Provides
    @Singleton
    public GetFormDefinitionByOrgIdActivity provideGetFormDefinitionByOrgIdActivity(FormDefinitionDAO formDefinitionDAO){
        return new GetFormDefinitionByOrgIdActivity(formDefinitionDAO);
    }

    @Provides
    @Singleton
    public GetFormDefinitionByOrgIdFormIdActivity provideGetFormDefinitionByOrgIdFormIdActivity(FormDefinitionDAO formDefinitionDAO){
        return new GetFormDefinitionByOrgIdFormIdActivity(formDefinitionDAO);
    }

    @Provides
    @Singleton
    public GetFormDefinitionByOrgIdFormIdVersionActivity provideGetFormDefinitionByOrgIdFormIdVersionActivity(FormDefinitionDAO formDefinitionDAO, S3Helper s3Helper){
        return new GetFormDefinitionByOrgIdFormIdVersionActivity(formDefinitionDAO, s3Helper);
    }


    @Provides
    @Singleton
    public ValidateUpdateFormDefinitionRequest provideValidateUpdateFormDefinitionActivity() {
        return new ValidateUpdateFormDefinitionRequest();
    }

    @Provides
    @Singleton
    public UpdateFormDefinitionActivity provideUpdateFormDefinitionActivity(FormDefinitionDAO formDefinitionDAO, ValidateUpdateFormDefinitionRequest validateUpdateFormDefinitionRequest, S3Helper s3Helper){
        return new UpdateFormDefinitionActivity(formDefinitionDAO, validateUpdateFormDefinitionRequest, s3Helper);
    }

    @Provides
    @Singleton
    public DeleteFormDefinitionActivity provideDeleteFormDefinitionActivity(FormDefinitionDAO formDefinitionDAO, S3Helper s3Helper){
        return new DeleteFormDefinitionActivity(formDefinitionDAO, s3Helper);
    }

    @Provides
    @Singleton
    public CreateFormDefinitionActiveActivity provideCreateFormDefinitionActiveActivity(FormDefinitionDAO formDefinitionDAO){
        return new CreateFormDefinitionActiveActivity(formDefinitionDAO);
    }

}
