package com.github.diszexuf.core.web;

import com.github.diszexuf.core.exception.GeneralException;
import com.github.diszexuf.core.web.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @EventListener(GeneralException.class)
    public ResponseEntity<ErrorResponse> generalExceptionHandler(GeneralException e) {
        log.error("General exception", e);

        return response(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<ErrorResponse> response(HttpStatus status, String message) {
        var errorResponseBody = ErrorResponse.builder().message(message).build();

        return ResponseEntity.status(status).body(errorResponseBody);
    }
}
