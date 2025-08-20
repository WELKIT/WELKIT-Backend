package welkit_server.global.exception.model;

import welkit_server.global.exception.message.ErrorMessage;

public class ConflictException extends WelkitException {

    public ConflictException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
