package com.lucas.gym_management.user.infrastructure.adapters.inbound.rest;

import com.lucas.gym_management.user.application.domain.model.exceptions.DomainException;
import com.lucas.gym_management.user.application.exceptions.ConflictException;
import com.lucas.gym_management.user.application.exceptions.NotAuthorizedException;
import com.lucas.gym_management.user.application.exceptions.NotFoundException;
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

@ControllerAdvice(basePackages = "com.lucas.gym_management.user")
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ConflictException.class })
    protected ResponseEntity<ProblemDetail> handleBusinessException(final ConflictException ex, final WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setType(URI.create("https://example.com/conflict"));
        problemDetail.setTitle("Conflict Exception");
        problemDetail.setInstance(URI.create(
                request.getDescription(false).replace("uri=","")));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<ProblemDetail> handleNotFoundException(final NotFoundException ex, final WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("https://example.com/not-found"));
        problemDetail.setTitle("Not Found");
        problemDetail.setInstance(URI.create(
                request.getDescription(false).replace("uri=","")));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(value = { NotAuthorizedException.class })
    protected ResponseEntity<ProblemDetail> handleNotAuthorizedException(final NotAuthorizedException ex, final WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problemDetail.setType(URI.create("https://example.com/forbidden"));
        problemDetail.setTitle("Forbidden");
        problemDetail.setInstance(URI.create(
                request.getDescription(false).replace("uri=","")));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }

    @ExceptionHandler(value = { DomainException.class })
    protected ResponseEntity<ProblemDetail> handleDomainException(final DomainException ex, final WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setType(URI.create("https://example.com/not-found"));
        problemDetail.setTitle("Bad request");
        problemDetail.setInstance(URI.create(
                request.getDescription(false).replace("uri=","")));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleGeneralException(final RuntimeException ex, final WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred");
        problemDetail.setType(URI.create("https://example.com/internal-server-error"));
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request){

        List<Map<String, String>> invalidParams = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> param = new HashMap<>();
                    param.put("field", error.getField());
                    param.put("message", error.getDefaultMessage());

                    return param;
                })
                .collect(Collectors.toUnmodifiableList());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation Failed");
        problemDetail.setType(URI.create("https://example.com/validation-error"));
        problemDetail.setTitle("Validation Error");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setProperty("invalid-params: ",  invalidParams);
        problemDetail.setProperty("timestamp", Instant.now().toString());

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }
}
