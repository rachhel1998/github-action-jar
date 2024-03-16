package com.wazo.services.formdefinition.exception.type;

import lombok.Getter;
import org.apache.http.HttpStatus;

public enum ErrorCode {

    INVALID_REQUEST("INVALID_REQUEST_EXCEPTION", "Invalid request provided in input - %s", HttpStatus.SC_BAD_REQUEST),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND_EXCEPTION", "Resource not found - %s", HttpStatus.SC_NOT_FOUND);

    @Getter
    private String errorCode;

    @Getter
    private String errorMessage;

    @Getter
    private int httpStatus;

    ErrorCode(String errorCode, String errorMessage, int httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

}
