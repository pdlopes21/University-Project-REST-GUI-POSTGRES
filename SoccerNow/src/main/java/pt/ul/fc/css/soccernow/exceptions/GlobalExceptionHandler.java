package pt.ul.fc.css.soccernow.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JogoInvalidoException.class)
    public ResponseEntity<String> handleJogoInvalido(JogoInvalidoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}