package com.wazo.services.formdefinition.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.wazo.services.formdefinition.dagger.AppComponent;
import com.wazo.services.formdefinition.dagger.DaggerAppComponent;
import com.wazo.services.formdefinition.model.ApiResponse;

    public class LambdaMainHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static AppComponent appComponent;
    public static void initializeDagger(){
        appComponent = DaggerAppComponent.builder().build();
    }
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        initializeDagger();

        String requestBody = input.getBody() != null ? input.getBody() : "{}";
        switch (input.getHttpMethod().concat(":").concat(input.getResource())){
            case "GET:/org/{orgId}/form-definition-service":
                return appComponent.getFormDefinitionByOrgIdActivity().run(input.getPathParameters().get("orgId")).proxyResponse();

            case "POST:/org/{orgId}/form-definition-service":
                return appComponent.createFormDefinitionActivity().run(input.getPathParameters().get("orgId"), requestBody).proxyResponse();

            case "GET:/org/{orgId}/form-definition-service/{formId}":
                return appComponent.getFormDefinitionByOrgIdFormIdActivity().run(input.getPathParameters().get("orgId"), input.getPathParameters().get("formId")).proxyResponse();

            case "GET:/org/{orgId}/form-definition-service/{formId}/{formVersion}":
                return appComponent.getGetFormDefinitionByOrgIdFormIdVersionActivity().run(input.getPathParameters().get("orgId"), input.getPathParameters().get("formId"), input.getPathParameters().get("formVersion")).proxyResponse();

            case "PUT:/org/{orgId}/form-definition-service/{formId}/{formVersion}":
                return appComponent.updateFormDefinitionActivity().run(input.getPathParameters().get("orgId"), input.getPathParameters().get("formId"), input.getPathParameters().get("formVersion"), requestBody).proxyResponse();

            case "DELETE:/org/{orgId}/form-definition-service/{formId}/{formVersion}":
                return appComponent.deleteFormDefinitionActivity().run(input.getPathParameters().get("orgId"), input.getPathParameters().get("formId"), input.getPathParameters().get("formVersion")).proxyResponse();
            case "PUT:/org/{orgId}/form-definition-service/{formId}/{formVersion}/active":
                return appComponent.createFormDefinitionActiveActivity().run(input.getPathParameters().get("orgId"), input.getPathParameters().get("formId"), input.getPathParameters().get("formVersion")).proxyResponse();
            default:
                return ApiResponse.builder().status(400).message("Invalid request method!").data(input).build().proxyResponse();

        }
    }
}
