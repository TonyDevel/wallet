package com.wallet.controller;

import com.wallet.exception.AccountNotFoundException;
import com.wallet.exception.InsufficientFundsToWithdrawException;
import com.wallet.model.dto.ValidationErrorResponse;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public String handleAccountNotFound(AccountNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InsufficientFundsToWithdrawException.class)
    public String handleInsufficientFunds(InsufficientFundsToWithdrawException exception) {
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<ValidationErrorResponse> errorResponses = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorResponses.add(new ValidationErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return ResponseEntity.badRequest().body(errorResponses);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintError(ConstraintViolationException exception) {
        List<ValidationErrorResponse> errorResponses = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            errorResponses.add(new ValidationErrorResponse(
                    constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage())
            );
        }
        return ResponseEntity.badRequest().body(errorResponses);
    }

}
