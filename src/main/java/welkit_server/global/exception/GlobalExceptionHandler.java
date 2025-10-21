package welkit_server.global.exception;

import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import welkit_server.global.dto.ErrorResponse;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(final BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(e.getErrorMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(final UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.of(e.getErrorMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(final ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.of(e.getErrorMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(e.getErrorMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(final ConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.of(e.getErrorMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e) {

        FieldError fieldError = e.getBindingResult().getFieldError();
        ErrorMessage errorMessage;

        if (fieldError == null) {
            errorMessage = ErrorMessage.WK_VALIDATION_MISSING;
        } else if ("password".equals(fieldError.getField())) {
            String rejectedValue = String.valueOf(fieldError.getRejectedValue());

            if (rejectedValue.length() < 8) {
                errorMessage = ErrorMessage.INVALID_PASSWORD_MIN_LENGTH;
            } else if (rejectedValue.length() > 64) {
                errorMessage = ErrorMessage.INVALID_PASSWORD_MAX_LENGTH;
            } else if (!rejectedValue.matches(".*[0-9].*")) {
                errorMessage = ErrorMessage.INVALID_PASSWORD_NUMBER;
            } else if (!rejectedValue.matches(".*[!@#$%^&*(),.?\":{}|<>\\-_=+\\[\\]{};:'\",.<>/?].*")) {
                errorMessage = ErrorMessage.INVALID_PASSWORD_SPECIAL_CHAR;
            } else {
                errorMessage = ErrorMessage.WK_VALIDATION_MISSING;
            }
        } else if ("Size".equals(fieldError.getCode())) {
            String field = fieldError.getField();
            String rejectedValue = fieldError.getRejectedValue() != null
                    ? fieldError.getRejectedValue().toString() : "";

            errorMessage = switch (field) {
                // 용어 이름 / 정의
                case "name" -> rejectedValue.length() > 50
                        ? ErrorMessage.TERM_NAME_MAX_LENGTH
                        : ErrorMessage.TERM_NAME_MIN_LENGTH;
                case "definition" -> rejectedValue.length() > 500
                        ? ErrorMessage.TERM_DEFINITION_MAX_LENGTH
                        : ErrorMessage.TERM_DEFINITION_MIN_LENGTH;

                // 인사이트 카드
                case "title" -> rejectedValue.length() > 30
                        ? ErrorMessage.INSIGHT_CARD_TITLE_MAX_LENGTH
                        : ErrorMessage.INSIGHT_CARD_TITLE_MIN_LENGTH;
                case "description" -> rejectedValue.length() > 500
                        ? ErrorMessage.INSIGHT_CARD_DESCRIPTION_MAX_LENGTH
                        : ErrorMessage.INSIGHT_CARD_DESCRIPTION_MIN_LENGTH;
                case "note" -> rejectedValue.length() > 200
                        ? ErrorMessage.INSIGHT_CARD_NOTE_MAX_LENGTH
                        : ErrorMessage.INSIGHT_CARD_NOTE_MIN_LENGTH;

                // 회고
                case "content" -> rejectedValue.length() > 2000
                        ? ErrorMessage.RETROSPECTIVE_CONTENT_MAX_LENGTH
                        : ErrorMessage.RETROSPECTIVE_CONTENT_MIN_LENGTH;

                // 카테고리 이름
                case "categoryName" -> rejectedValue.length() > 20
                        ? ErrorMessage.CATEGORY_NAME_MAX_LENGTH
                        : ErrorMessage.CATEGORY_NAME_MIN_LENGTH;

                default -> ErrorMessage.WK_VALIDATION_LENGTH_EXCEEDED;
            };
        } else {
            errorMessage = switch (fieldError.getCode()) {
                case "NotNull", "NotBlank" -> ErrorMessage.WK_VALIDATION_NULL_OR_BLANK;
                case "Email" -> ErrorMessage.WK_VALIDATION_EMAIL;
                default -> ErrorMessage.WK_VALIDATION_MISSING;
            };
        }

        return ResponseEntity.status(errorMessage.getStatus())
                .body(ErrorResponse.of(errorMessage));
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(jakarta.validation.ConstraintViolationException e) {
        ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
        String field = violation.getPropertyPath().toString();
        String message = violation.getMessage();

        ErrorMessage errorMessage = message.contains("크기가")
                ? switch (field) {
            case "searchPosts.keyword" -> ErrorMessage.COMMUNITY_SEARCH_KEYWORD_SIZE;
            default -> ErrorMessage.WK_VALIDATION_LENGTH_EXCEEDED;
        }
                : ErrorMessage.WK_VALIDATION_MISSING;

        return ResponseEntity.status(errorMessage.getStatus())
                .body(ErrorResponse.of(errorMessage));
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(ErrorMessage.WK_ENUM_VALUE_BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(ErrorMessage.INTERNAL_SERVER_ERROR));
    }

}
