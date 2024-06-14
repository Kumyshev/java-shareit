package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({ UnsupportedStatusException.class })
    public ResponseEntity<Object> handleNoHandlerFoundException(UnsupportedStatusException ex) {
        UnsupportedStatusError error = new UnsupportedStatusError("Unknown state: UNSUPPORTED_STATUS");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
