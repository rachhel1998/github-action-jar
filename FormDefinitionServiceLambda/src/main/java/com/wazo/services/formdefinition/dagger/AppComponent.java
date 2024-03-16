package com.wazo.services.formdefinition.dagger;

import com.wazo.services.formdefinition.activity.*;
import com.wazo.services.formdefinition.handler.LambdaMainHandler;
import dagger.Component;

import javax.inject.Singleton;


@Singleton
@Component(modules = {EnvModule.class, AppModule.class})
public interface AppComponent {
    void inject(LambdaMainHandler lambdaMainHandler);
    CreateFormDefinitionActivity createFormDefinitionActivity();
    UpdateFormDefinitionActivity updateFormDefinitionActivity();
    DeleteFormDefinitionActivity deleteFormDefinitionActivity();
    GetFormDefinitionByOrgIdActivity getFormDefinitionByOrgIdActivity();
    GetFormDefinitionByOrgIdFormIdActivity getFormDefinitionByOrgIdFormIdActivity();
    GetFormDefinitionByOrgIdFormIdVersionActivity getGetFormDefinitionByOrgIdFormIdVersionActivity();

    CreateFormDefinitionActiveActivity createFormDefinitionActiveActivity();
}
