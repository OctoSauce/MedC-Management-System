package exceptions;

public class UserNotRegisteredException extends RuntimeException {
    public UserNotRegisteredException() {
        super("This user has not been registered.");
    }
}
