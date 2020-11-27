package com.teste.DesafioBento.api;

import com.teste.DesafioBento.error.ApiError;
import com.teste.DesafioBento.error.RepeatedDataException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleUniqueErrors(DataIntegrityViolationException e) {
        PSQLException rootCause = (PSQLException) e.getRootCause();
        if (!rootCause.getSQLState().equals("23505"))
            return ResponseEntity.badRequest().body(e);
        String fieldName = rootCause.getServerErrorMessage()
                .getDetail().replaceAll("(.*)\\((\\w+)\\)(.*)", "$2");
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                "O seguinte campo deve ser único: " + fieldName);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(RepeatedDataException.class)
    public ResponseEntity handleRepeatedValues(RepeatedDataException e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.badRequest().body(apiError);
    }
}
