package com.wazo.services.formdefinition;

import com.wazo.services.formdefinition.clients.DynamoDb;
import com.wazo.services.formdefinition.dagger.AppComponent;
import com.wazo.services.formdefinition.dao.FormDefinitionDAO;
import com.wazo.services.formdefinition.dao.entity.FormDefinition;
import com.wazo.services.formdefinition.dao.impl.FormDefinitionDAOImpl;

import java.util.List;

public class App {

    private static AppComponent appComponent;

    public static void main(String[] args) {
        FormDefinitionDAO formDefinitionDAO = new FormDefinitionDAOImpl(new DynamoDb());
        List<FormDefinition> lf = formDefinitionDAO.getByOrgIdFormIdFormDefinition("12345", "87e7c342-fd67-4c98-8146-0620829c0151");
        System.out.println(lf.size());
        System.out.println(lf);
//        FormDefinition formDefinition = formDefinitionDAO.getByOrgIdFormIdVersionFormDefinition("12345", "6c827052-f582-4c9c-a268-d8a0430ebee5", "1710078159");
//        System.out.println(formDefinition);
    }
}
