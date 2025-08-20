package welkit_server.global.exception.model;

import welkit_server.global.exception.message.ErrorMessage;

public class UnauthorizedException extends WelkitException {

    public UnauthorizedException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }

}
