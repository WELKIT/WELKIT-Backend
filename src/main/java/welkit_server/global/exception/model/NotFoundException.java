package welkit_server.global.exception.model;

import welkit_server.global.exception.message.ErrorMessage;

public class NotFoundException extends WelkitException {

    public NotFoundException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
