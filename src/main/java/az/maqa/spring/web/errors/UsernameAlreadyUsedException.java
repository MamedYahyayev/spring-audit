package az.maqa.spring.web.errors;

public class UsernameAlreadyUsedException extends BadRequestException {

    public UsernameAlreadyUsedException() {
        super("Username is already in use");
    }
}
