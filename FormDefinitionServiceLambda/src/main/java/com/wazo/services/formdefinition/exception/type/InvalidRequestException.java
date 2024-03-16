package com.wazo.services.formdefinition.exception.type;

public class InvalidRequestException extends FormDefinitionServiceException {

    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public InvalidRequestException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public InvalidRequestException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

}
