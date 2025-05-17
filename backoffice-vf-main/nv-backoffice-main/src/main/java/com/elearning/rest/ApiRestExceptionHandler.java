package com.elearning.rest;

import com.elearning.exception.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiRestExceptionHandler {

    public static class ErrorDto {
        private String code, message;
        public ErrorDto() {}
        public ErrorDto(String c, String m) { code = c; message = m; }
        public String getCode() { return code; }
        public void setCode(String c) { code = c; }
        public String getMessage() { return message; }
        public void setMessage(String m) { message = m; }
    }

    @ExceptionHandler({ ResourceNotFoundException.class, NoSuchElementException.class })
    public ResponseEntity<ErrorDto> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("NOT_FOUND", ex.getMessage()));
    }
}
