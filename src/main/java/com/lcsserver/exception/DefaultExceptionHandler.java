package com.lcsserver.exception;

import com.lcsserver.dto.LcsErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = LcsException.class)
    public ResponseEntity<LcsErrorResponse> handleLcsException(LcsException lcsException) {
        LcsErrorResponse errorResponse = new LcsErrorResponse(lcsException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
