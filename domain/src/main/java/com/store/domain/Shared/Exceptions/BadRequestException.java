package com.store.domain.Shared.Exceptions; 
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private List<String> errors = new ArrayList<>();

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(List<String> errors) {
        super("Erros de validação encontrados");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}