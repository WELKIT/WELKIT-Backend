package welkit_server.global.exception.model;

import welkit_server.global.exception.message.ErrorMessage;

public class ForbiddenException extends WelkitException {

    public ForbiddenException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
