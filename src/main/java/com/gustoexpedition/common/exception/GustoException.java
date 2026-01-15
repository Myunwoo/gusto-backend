package com.gustoexpedition.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * packageName    : com.gustoexpedition.exception
 * fileName       : GustoException
 * author         : fddsg
 * date           : 2026-01-15
 * description    :
 */
public class GustoException extends RuntimeException {
    public GustoException(String message) {
        super(message);
    }
}
