package welkit_server.global.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    // 공통 에러 (WK)
    WK_ENUM_VALUE_BAD_REQUEST(400, "WK1001", "요청한 값이 유효하지 않습니다."),
    WK_VALIDATION_MISSING(400, "WK1002", "요청 값이 비어있습니다."),
    WK_VALIDATION_NULL_OR_BLANK(400, "WK1003", "필수 요청 값이 누락되었습니다."),
    WK_VALIDATION_LENGTH_EXCEEDED(400, "WK1004", "요청 값이 길이를 초과했습니다."),
    WK_VALIDATION_EMAIL(400, "WK1005", "유효하지 않은 이메일 형식입니다."),

    // 로그인 (USR)
    INVALID_CREDENTIAL(400, "USR1001", "이메일 또는 비밀번호가 일치하지 않습니다."),

    // 회원가입 (USR)
    DUPLICATE_EMAIL(400, "USR2001", "이미 사용중인 이메일 주소입니다."),
    INVALID_PASSWORD_FORMAT(400, "USR2002", "비밀번호 형식이 맞지 않습니다."),
    INVALID_EMAIL_CODE(400, "USR2003", "이메일 인증 코드가 올바르지 않습니다."),
    EXPIRED_EMAIL_CODE(400, "USR2004", "이메일 인증 코드가 만료되었습니다."),
    INVALID_EMAIL_VERIFICATION(400, "USR2005", "이메일 인증에 실패했습니다."),
    USR_EMAIL_COMPANY_DOMAIN_INVALID(400, "USR2006", "범용 이메일은 회사 인증 회원가입이 불가능합니다."),

    // 용어 사전 (TRM)
    TERM_NOT_FOUND(404, "TRM1001", "해당 용어를 찾을 수 없습니다."),

    // 인사이트 카드 (INS)
    INSIGHT_CARD_NOT_FOUND(404, "INS1001", "해당 카드를 찾을 수 없습니다."),

    // 회고 (RET)
    RETROSPECTIVE_NOT_FOUND(404, "RET1001", "해당 회고를 찾을 수 없습니다."),
    RETROSPECTIVE_INVALID_DATE_RANGE(400, "RET1002", "회고의 날짜 범위가 올바르지 않습니다."),

    // 커뮤니티 (CMN)
    COMMUNITY_NOT_FOUND(404, "CMN1001", "해당 게시물을 찾을 수 없습니다."),
    COMMUNITY_NO_PERMISSION(401, "CMN1002", "권한이 없습니다."),

    // 마이페이지 (MYP)
    MYP_NOTICE_NOT_FOUND(404, "MYP1001", "해당 공지를 찾을 수 없습니다."),
    MYP_POST_NOT_FOUND(404, "MYP1002", "해당 게시물을 찾을 수 없습니다."),
    MYP_INVALID_EMAIL_CODE(400, "MYP1003", "이메일 인증 코드가 올바르지 않습니다."),
    MYP_EXPIRED_EMAIL_CODE(400, "MYP1004", "이메일 인증 코드가 만료되었습니다."),
    MYP_INVALID_DATE_RANGE(400, "MYP1005", "공지 사항의 날짜 범위가 올바르지 않습니다."),

    // 인증/인가 (AUTH)
    SESSION_EXPIRED(401, "AUTH1001", "세션이 만료되었습니다. 다시 로그인해주세요."),
    LOGIN_REQUIRED(401, "AUTH1002", "로그인 후 진행해주세요."),
    ADMIN_PRIVILEGE_REQUIRED(403, "AUTH1003", "관리자 권한이 필요합니다."),

    // 서버 에러
    INTERNAL_SERVER_ERROR(500, "INT5000", "서버 내부 오류가 발생했습니다.");

    private final int status;    // HTTP 상태 코드
    private final String code;   // 에러 코드
    private final String message; // 에러 메시지
}