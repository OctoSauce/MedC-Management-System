package exceptions;

public class NumberNotValidException extends RuntimeException {
    public NumberNotValidException() {
        super("Mobile number should be a valid 10-digit number.");
    }
}
