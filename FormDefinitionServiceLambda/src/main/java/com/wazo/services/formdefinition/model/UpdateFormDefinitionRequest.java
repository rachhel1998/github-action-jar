package com.wazo.services.formdefinition.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateFormDefinitionRequest {
    private String formTitle;
    private String formDescription;
    private String formMetaData;
    private String formDefinition;
    private String stateFlag;
    private String createdBy;
}
