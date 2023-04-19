package exceptions;

public class InsufficientQuantityException extends RuntimeException {
    public InsufficientQuantityException() {
        super("Medicine not available.");
    }
}
