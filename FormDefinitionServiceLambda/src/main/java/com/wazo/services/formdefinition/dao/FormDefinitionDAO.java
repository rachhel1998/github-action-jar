package com.wazo.services.formdefinition.dao;
import com.wazo.services.formdefinition.dao.entity.FormDefinition;

import java.util.List;

public interface FormDefinitionDAO {
    FormDefinition saveFormDefinition(FormDefinition formDefinition);

    List<FormDefinition> getByOrgIdFormDefinition(String orgId);

    List<FormDefinition> getByOrgIdFormIdFormDefinition(String orgId, String formId);
    FormDefinition getByOrgIdFormIdVersionFormDefinition(String orgId, String formId, String formVersion);

    void deleteFormDefinition(String orgId, String formId, String formVersion);

}
