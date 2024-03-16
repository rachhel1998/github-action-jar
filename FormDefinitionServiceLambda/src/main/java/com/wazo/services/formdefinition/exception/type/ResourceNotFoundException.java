package com.wazo.services.formdefinition.exception.type;

public class ResourceNotFoundException extends FormDefinitionServiceException {

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ResourceNotFoundException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public ResourceNotFoundException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }
}
