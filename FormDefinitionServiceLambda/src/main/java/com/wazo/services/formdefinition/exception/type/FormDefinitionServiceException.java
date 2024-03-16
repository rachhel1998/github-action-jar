package com.wazo.services.formdefinition.exception.type;

public class FormDefinitionServiceException extends RuntimeException {

    private final ErrorCode errorCode;

    public FormDefinitionServiceException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public FormDefinitionServiceException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public FormDefinitionServiceException(String message, Throwable cause, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public FormDefinitionServiceException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return super.getMessage();
    }
}
