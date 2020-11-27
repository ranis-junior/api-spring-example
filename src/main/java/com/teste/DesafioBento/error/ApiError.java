package com.teste.DesafioBento.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {
    private String status;
    private List<String> errors;


    public ApiError(HttpStatus status, String error) {
        this.status = status.value() + " - " + status.getReasonPhrase();
        errors = Arrays.asList(error);
    }

    public ApiError(List<ObjectError> erros, HttpStatus status) {
        this.status = status.value() + " - " + status.getReasonPhrase();
        this.errors = erros.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
    }
}