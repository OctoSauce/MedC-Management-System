package exceptions;

public class MedicineAlreadyExistsException extends RuntimeException {
    public MedicineAlreadyExistsException() {
        super("Medicine already exists.");
    }
}
