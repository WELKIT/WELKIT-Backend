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
    WK_NO_PERMISSION(403, "WK1006","권한이 없습니다. "),
    INVALID_PASSWORD_MIN_LENGTH(400, "WK1007", "비밀번호는 최소 8자 이상이어야 합니다."),
    INVALID_PASSWORD_MAX_LENGTH(400, "WK1008", "비밀번호는 최대 64자를 초과할 수 없습니다."),
    INVALID_PASSWORD_NUMBER(400, "WK1009", "비밀번호는 최소 1개의 숫자를 포함해야 합니다."),
    INVALID_PASSWORD_SPECIAL_CHAR(400, "WK1010", "비밀번호는 최소 1개의 특수문자를 포함해야 합니다."),

    // 로그인 (USR)
    INVALID_CREDENTIAL(400, "USR1001", "이메일 또는 비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(404, "USR1002", "사용자를 찾을 수 없습니다."),

    // 회원가입 (USR)
    DUPLICATE_EMAIL(400, "USR2001", "이미 사용중인 이메일 주소입니다."),
    INVALID_EMAIL_CODE(400, "USR2002", "이메일 인증 코드가 올바르지 않습니다."),
    EXPIRED_EMAIL_CODE(400, "USR2003", "이메일 인증 코드가 만료되었습니다."),
    INVALID_EMAIL_VERIFICATION(400, "USR2004", "이메일 인증에 실패했습니다."),
    USR_EMAIL_COMPANY_DOMAIN_INVALID(400, "USR2005", "범용 이메일은 회사 인증 회원가입이 불가능합니다."),

    // 용어 사전 (TRM)
    TERM_NOT_FOUND(404, "TRM1001", "해당 용어를 찾을 수 없습니다."),
    TERM_NAME_MIN_LENGTH(400, "TRM1002", "용어 이름은 최소 1자 이상이어야 합니다."),
    TERM_NAME_MAX_LENGTH(400, "TRM1003", "용어 이름은 최대 50자를 초과할 수 없습니다."),
    TERM_DEFINITION_MIN_LENGTH(400, "TRM1004", "용어 정의는 최소 1자 이상이어야 합니다."),
    TERM_DEFINITION_MAX_LENGTH(400, "TRM1005", "용어 정의는 최대 500자를 초과할 수 없습니다."),
    CATEGORY_NAME_MIN_LENGTH(400, "TRM1006", "카테고리 이름은 최소 1자 이상이어야 합니다."),
    CATEGORY_NAME_MAX_LENGTH(400, "TRM1007", "카테고리 이름은 최대 20자를 초과할 수 없습니다."),
    CATEGORY_NOT_FOUND(404,"TRM1008","존재하지 않는 카테고리 입니다"),

    // 인사이트 카드 (INS)
    INSIGHT_CARD_NOT_FOUND(404, "INS1001", "해당 카드를 찾을 수 없습니다."),
    INSIGHT_CARD_TITLE_MIN_LENGTH(400, "INS1002", "카드 제목은 최소 1자 이상이어야 합니다."),
    INSIGHT_CARD_TITLE_MAX_LENGTH(400, "INS1003", "카드 제목은 최대 30자를 초과할 수 없습니다."),
    INSIGHT_CARD_DESCRIPTION_MIN_LENGTH(400, "INS1004", "카드 설명은 최소 1자 이상이어야 합니다."),
    INSIGHT_CARD_DESCRIPTION_MAX_LENGTH(400, "INS1005", "카드 설명은 최대 500자를 초과할 수 없습니다."),
    INSIGHT_CARD_NOTE_MIN_LENGTH(400, "INS1006", "카드 비고는 최소 0자 이상이어야 합니다."),
    INSIGHT_CARD_NOTE_MAX_LENGTH(400, "INS1007", "카드 비고는 최대 200자를 초과할 수 없습니다."),

    // 회고 (RET)
    RETROSPECTIVE_NOT_FOUND(404, "RET1001", "해당 회고를 찾을 수 없습니다."),
    RETROSPECTIVE_CONTENT_MIN_LENGTH(400, "RET1002", "회고 내용은 최소 1자 이상이어야 합니다."),
    RETROSPECTIVE_CONTENT_MAX_LENGTH(400, "RET1003", "회고 내용은 최대 2000자를 초과할 수 없습니다."),
    RETROSPECTIVE_INVALID_DATE_RANGE(400, "RET1004", "회고의 날짜 범위가 올바르지 않습니다."),

    // 커뮤니티 (CMN)
    COMMUNITY_POST_NOT_FOUND(404, "CMN1001", "해당 게시글을 찾을 수 없습니다."),
    COMMUNITY_COMMENT_NOT_FOUND(404, "CMN1002", "해당 댓글을 찾을 수 없습니다."),

    // 마이페이지 (MYP)
    MYP_NOTICE_NOT_FOUND(404, "MYP1001", "해당 공지를 찾을 수 없습니다."),
    MYP_POST_NOT_FOUND(404, "MYP1002", "해당 게시물을 찾을 수 없습니다."),
    MYP_INVALID_DATE_RANGE(400, "MYP1003", "공지 사항의 날짜 범위가 올바르지 않습니다."),
    MYP_LOCK_PIN_NOT_FOUND(400, "MYP1004", "PIN이 설정되어 있지 않아 잠금 기능을 사용할 수 없습니다"),
    MYP_INVALID_LOCK_PIN(400, "MYP1005","PIN이 올바르지 않습니다."),
    MYP_ALREADY_COMPANY_EMAIL_USER(400, "MYP1006","이미 회사 이메일로 인증된 사용자입니다."),

    // 인증/인가 (AUTH)
    SESSION_EXPIRED(401, "AUTH1001", "세션이 만료되었습니다. 다시 로그인해주세요."),
    LOGIN_REQUIRED(401, "AUTH1002", "로그인 후 진행해주세요."),
    ADMIN_PRIVILEGE_REQUIRED(403, "AUTH1003", "관리자 권한이 필요합니다."),
    INVALID_TOKEN(401, "AUTH1004", "유효하지 않은 토큰입니다."),

    // 서버 에러
    INTERNAL_SERVER_ERROR(500, "INT5000", "서버 내부 오류가 발생했습니다.");

    private final int status;    // HTTP 상태 코드
    private final String code;   // 에러 코드
    private final String message; // 에러 메시지
}
