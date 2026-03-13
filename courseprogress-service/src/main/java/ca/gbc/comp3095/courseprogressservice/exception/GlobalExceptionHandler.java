package ca.gbc.comp3095.courseprogressservice.exception;

/* JAMES - 
* This helps ensure:
*                  - predictable JSON errors
*                  - no stack traces
*                  - clear field-level validation feedback
* */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourseProgressNotFoundException.class)
    public ResponseEntity<?> handleNotFound(CourseProgressNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}