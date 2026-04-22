package com.lucas.gym_management.gym.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.gym.application.domain.model.exceptions.GymClassNotAssociatedException;
import com.lucas.gym_management.gym.application.domain.model.exceptions.UserNotMemberException;
import com.lucas.gym_management.gym.application.exceptions.GymNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice(basePackages = "com.lucas.gym_management.gym")
public class GymExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { GymClassNotAssociatedException.class })
    protected ResponseEntity<ProblemDetail> handleSystemBaseException(GymClassNotAssociatedException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.PRECONDITION_FAILED, ex.getMessage());
        problemDetail.setType(URI.create("https://api.example.com/conflict/"));
        problemDetail.setTitle("Conflict Exception");
        problemDetail.setInstance(URI.create(
                request.getDescription(false).replace("uri=","")));
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(problemDetail);
    }

    @ExceptionHandler(value = { GymNotFoundException.class })
    protected ResponseEntity<ProblemDetail> handleSystemBaseException(GymNotFoundException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("https://api.example.com/not-found/"));
        problemDetail.setTitle("Not Found Exception");
        problemDetail.setInstance(URI.create(
                request.getDescription(false).replace("uri=","")));
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(value = { UserNotMemberException.class })
    protected ResponseEntity<ProblemDetail> handleSystemBaseException(UserNotMemberException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setType(URI.create("https://api.example.com/conflict"));
        problemDetail.setTitle("Conflict Exception");
        problemDetail.setInstance(URI.create(
                request.getDescription(false).replace("uri=","")));
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<Map<String, String>> invalidParams = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> {
                    Map<String, String> param = new HashMap<>();
                    param.put("field", err.getField());
                    param.put("message", err.getDefaultMessage());
                    return param;
                })
                .collect(Collectors.toList());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        problemDetail.setType(URI.create("https://api.example.com/errors/validation-error"));
        problemDetail.setTitle("Validation error");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=","")));
        problemDetail.setProperty("invalid-params", invalidParams);
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }
}
