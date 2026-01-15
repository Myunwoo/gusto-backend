package com.gustoexpedition.common.exception;

/**
 * packageName    : com.gustoexpedition.common.exception
 * fileName       : ErrorResponse
 * author         : fddsg
 * date           : 2026-01-15
 * description    :
 */
public class ErrorResponse {
    private final String errorCode;
    private final String message;

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
