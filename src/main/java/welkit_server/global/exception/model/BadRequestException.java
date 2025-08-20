package welkit_server.global.exception.model;

import welkit_server.global.exception.message.ErrorMessage;

public class BadRequestException extends WelkitException {

    public BadRequestException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
