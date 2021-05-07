package com.user.counterservice.api;

import com.user.counterservice.api.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author Mateusz Zyla
 * @since 07.05.2021
 */
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ApiError> handleHttpClientErrorExceptionForbidden(HttpClientErrorException.Forbidden httpClientErrorException) {
        return new ResponseEntity<>(new ApiError(httpClientErrorException.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ApiError> handleHttpClientErrorExceptionNotFound(HttpClientErrorException.NotFound httpClientErrorException) {
        return new ResponseEntity<>(new ApiError(httpClientErrorException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.UnprocessableEntity.class)
    public ResponseEntity<ApiError> handleHttpClientErrorExceptionUnprocessableEntity(HttpClientErrorException.UnprocessableEntity httpClientErrorException) {
        return new ResponseEntity<>(new ApiError(httpClientErrorException.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ApiError> handleHttpClientErrorExceptionUnauthorized(HttpClientErrorException.Unauthorized httpClientErrorException) {
        return new ResponseEntity<>(new ApiError(httpClientErrorException.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
