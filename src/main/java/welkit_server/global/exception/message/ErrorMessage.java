package welkit_server.global.exception.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {
    /*
    Not Found Exception
     */
    USER_NOT_FOUND(40400, "사용자를 찾을 수 없습니다."),
    TERM_NOT_FOUND(40401, "해당 용어를 찾을 수 없습니다."),

    /*
    Bad Request Exception
     */
    ENUM_VALUE_BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "요청한 값이 유효하지 않습니다."),
    VALIDATION_REQUEST_MISSING_EXCEPTION(HttpStatus.BAD_REQUEST.value(), "요청 값이 유효하지 않습니다."),
    VALIDATION_REQUEST_NULL_OR_BLANK_EXCEPTION(40002,"요청 값이 비어있습니다."),
    VALIDATION_REQUEST_LENGTH_EXCEPTION(40003,"요청 값이 길이를 초과했습니다."),

    /*
    Conflict Exception
     */
    DUPLICATE_USER(40900, "이미 존재하는 사용자입니다."),

    /*
    Unauthorized Exception
     */
    SESSION_EXPIRED(40100, "세션이 만료되었습니다. 다시 로그인해주세요."),
    UN_LOGIN_EXCEPTION(40101, "로그인 후 진행해주세요."),

    /*
    Forbidden Exception
     */
    ADMIN_PRIVILEGE_REQUIRED(40300, "관리자 권한이 필요합니다."),

    /*
    500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다."),
    ;

    final int status;
    final String message;
}
