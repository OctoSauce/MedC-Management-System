package exceptions;

public class IdNotValidException extends RuntimeException {
    public IdNotValidException() {
        super("BITS ID should have 8 digits.");
    }
}
