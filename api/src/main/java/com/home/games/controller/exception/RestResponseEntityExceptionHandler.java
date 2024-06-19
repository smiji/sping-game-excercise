package com.home.games.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Will catch any thrown BadBetExceptions and return a 400 error response containing
 * the exception's message to the client
 */
@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final int RESP_CODE_BAD_REQUEST = HttpStatus.BAD_REQUEST.value();

    @ExceptionHandler(value = {BadBetException.class})
    protected ResponseEntity<Object> handleConflict(BadBetException ex, WebRequest request) {
        ErrorResp errorResp = ErrorResp.builder()
                .type("about:blank")
                .title("Bad Request")
                .status(RESP_CODE_BAD_REQUEST)
                .detail("Internal error , please contact administrator")
                .instance(((ServletWebRequest) request).getRequest().getRequestURI()).build();
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, errorResp, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Nullable
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResp errorResp = ErrorResp.builder()
                .type("about:blank")
                .title("Bad Request")
                .status(RESP_CODE_BAD_REQUEST)
                .detail("Invalid request content")
                .instance(((ServletWebRequest) request).getRequest().getRequestURI()).build();
        return this.handleExceptionInternal(ex, errorResp, headers, HttpStatus.BAD_REQUEST, request);
    }

}
