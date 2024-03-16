package com.wazo.services.formdefinition.exception;


import com.wazo.services.formdefinition.exception.type.FormDefinitionServiceException;
import dagger.internal.Preconditions;

public class ExceptionHandler {

    public FormDefinitionServiceException handleActivityThrowable(Throwable cause) {
        cause = Preconditions.checkNotNull(cause, "Cause should not be null");
        return (FormDefinitionServiceException) cause;
    }

}
