package welkit_server.global.dto;

import welkit_server.global.exception.message.ErrorMessage;

public record ErrorResponse(
        int status,
        String code,
        String message
) {
    public static ErrorResponse of(final ErrorMessage errorMessage) {
        return new ErrorResponse(
                errorMessage.getStatus(),
                errorMessage.getCode(),
                errorMessage.getMessage()
        );
    }

    public static ErrorResponse of(final int status, final String code, final String message) {
        return new ErrorResponse(status, code, message);
    }

}