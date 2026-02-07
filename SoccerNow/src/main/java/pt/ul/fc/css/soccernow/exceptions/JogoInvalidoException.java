package pt.ul.fc.css.soccernow.exceptions;

/**
 * Exceção quando o jogo não é valido (duas equipas iguais ou jogo não existente)
 */
public class JogoInvalidoException extends RuntimeException {
    public JogoInvalidoException(String message) {
        super(message);
    }
}
