package welkit_server.global.dto;

import welkit_server.global.exception.message.SuccessMessage;

public record SuccessResponse<T>(
        int status,
        String message,
        T data
) {
    public static <T> SuccessResponse of(final SuccessMessage successMessage, final T data) {
        return new SuccessResponse(successMessage.getStatus(), successMessage.getMessage(), data);
    }

    public static SuccessResponse of(final SuccessMessage successMessage) {
        return new SuccessResponse(successMessage.getStatus(), successMessage.getMessage(), null);
    }

    public static <T> SuccessResponse<T> of(final T data, final String message, final int status) {
        return new SuccessResponse<>(status, message, data);
    }

    public static <T> SuccessResponse<T> of(final SuccessMessage successMessage, final T data, final String formattedMessage) {
        return new SuccessResponse(successMessage.getStatus(), formattedMessage, data);
    }

}
