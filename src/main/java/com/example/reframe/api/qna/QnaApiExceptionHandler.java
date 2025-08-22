package com.example.reframe.api.qna;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class QnaApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> onValidation(MethodArgumentNotValidException e) {
        var first = e.getBindingResult().getFieldErrors().stream().findFirst();
        return ResponseEntity.badRequest().body(Map.of(
                "error", "validation_error",
                "message", first.map(f -> f.getField() + " " + f.getDefaultMessage()).orElse("invalid")
        ));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<?> onBadRequest(RuntimeException e) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", "bad_request",
                "message", e.getMessage()
        ));
    }
}
