package welkit_server.global.exception.model;

import lombok.Getter;
import welkit_server.global.exception.message.ErrorMessage;

@Getter
public class WelkitException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public WelkitException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

}
