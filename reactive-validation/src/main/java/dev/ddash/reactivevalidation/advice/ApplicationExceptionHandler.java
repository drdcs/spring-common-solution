package dev.ddash.reactivevalidation.advice;

import dev.ddash.reactivevalidation.exception.CustomerNotFoundException;
import dev.ddash.reactivevalidation.exception.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleException(CustomerNotFoundException e) {
        ProblemDetail problem
                = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        problem.setType(URI.create("http://example.com/problems/customer-not-found"));
        problem.setTitle("customer-not-found");
        problem.setDetail(e.getMessage());
        return problem;
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ProblemDetail> handleException(InvalidInputException e) {
        ProblemDetail problem
                = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problem.setType(URI.create("http://example.com/problems/invalid-input"));
        problem.setTitle("invalid-input");
        problem.setDetail(e.getMessage());
        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }
}
