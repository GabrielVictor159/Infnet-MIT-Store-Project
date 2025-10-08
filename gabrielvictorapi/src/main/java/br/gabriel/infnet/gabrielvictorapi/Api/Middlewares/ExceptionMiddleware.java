package br.gabriel.infnet.gabrielvictorapi.Api.Middlewares;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import br.gabriel.infnet.gabrielvictorapi.Application.DTO.Core.ErrorResponseDTO;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.BadRequestException;
import br.gabriel.infnet.gabrielvictorapi.Shared.Exceptions.OperationException;

@RestControllerAdvice 
public class ExceptionMiddleware {

  @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestException(BadRequestException ex, WebRequest request) {
        
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            HttpStatus.BAD_REQUEST.value(),
            ex.getErrors(), 
            ex.getMessage(), 
            request.getDescription(false).replace("uri=", "") 
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<ErrorResponseDTO> handleOperationException(OperationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            HttpStatus.BAD_REQUEST.value(),
            errors, 
            ex.getMessage(), 
            request.getDescription(false).replace("uri=", "") 
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        
        errors.add(ex.getMessage());
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            errors,
            "Ocorreu um erro inesperado no servidor.",
            request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}