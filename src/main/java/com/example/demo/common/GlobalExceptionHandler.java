package com.example.demo.common;

import com.example.demo.common.ValidationError.ValueBoundError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationError handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<ValueBoundError> errors = new ArrayList<>();
        BindingResult bindingResult = e.getBindingResult();
        for (ObjectError objectError : bindingResult.getGlobalErrors()) {
            ValueBoundError valueBoundError = ValueBoundError.globalErrorOf(objectError.getDefaultMessage());
            errors.add(valueBoundError);
        }
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            ValueBoundError valueBoundError = ValueBoundError.of(fieldError.getField(), fieldError.getDefaultMessage());
            errors.add(valueBoundError);
        }
        return ValidationError.builder()
                .type(e.getClass().getTypeName())
                .message("Validation failed")
                .path(request.getRequestURI())
                .errors(errors)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationError handleException(ConstraintViolationException e, HttpServletRequest request) {
        List<ValueBoundError> errors = new ArrayList<>();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            Path propertyPath = constraintViolation.getPropertyPath();
            ValueBoundError valueBoundError = ValueBoundError.of(
                    propertyPath.toString(),
                    constraintViolation.getMessage()
            );
            errors.add(valueBoundError);
        }
        return ValidationError.builder()
                .type(e.getClass().getTypeName())
                .message("Validation failed")
                .path(request.getRequestURI())
                .errors(errors)
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Error handleException(Exception e, HttpServletRequest request) {
        Optional<Throwable> cause = Optional.ofNullable(e.getCause());
        return Error.builder()
                .type(e.getClass().getTypeName())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .cause(cause.map(Throwable::getMessage).orElse(null))
                .build();
    }

}
