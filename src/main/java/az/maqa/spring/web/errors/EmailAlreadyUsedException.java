package az.maqa.spring.web.errors;

public class EmailAlreadyUsedException extends BadRequestException {

    public EmailAlreadyUsedException() {
        super("Email is already in use");
    }
}
