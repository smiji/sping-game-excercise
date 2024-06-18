package roxor.games.roulette.controller.exception;

public class BadBetException extends RuntimeException {
    public BadBetException(String message) {
        super(message);
    }
}
