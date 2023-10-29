package com.example.superheroes.controller;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

/**
 * Global exception handler for controllers in the application.
 */
@ControllerAdvice(basePackages = "com.example.superheroes.controller")
@ResponseBody
public class ControllerExceptionAdviser {

    /**
     * Handles exceptions of type ChangeSetPersister.NotFoundException, NullPointerException, and NoSuchElementException.
     *
     * @param e The exception to handle.
     * @return A ResponseEntity with a NOT_FOUND status and an error message.
     */
    @ExceptionHandler({ChangeSetPersister.NotFoundException.class, NullPointerException.class, NoSuchElementException.class})
    public ResponseEntity<String> notFoundException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions of type IllegalArgumentException.
     *
     * @param e The exception to handle.
     * @return A ResponseEntity with a BAD_REQUEST status and an error message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
