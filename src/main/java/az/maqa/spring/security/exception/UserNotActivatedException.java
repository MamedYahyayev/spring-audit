package az.maqa.spring.security.exception;

public class UserNotActivatedException extends RuntimeException {

    public UserNotActivatedException(String message) {
        super(message);
    }
}
