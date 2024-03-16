package com.wazo.services.formdefinition.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CreateFormDefinitionRequest {
    @NonNull
    private String formTitle;
    private String formDescription;
    private String formMetaData;
    private String formDefinition;
    private String stateFlag;
    private String createdBy;
}
