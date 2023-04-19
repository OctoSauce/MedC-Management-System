package exceptions;

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException() {
        super("Entry not found in database.");
    }
}
