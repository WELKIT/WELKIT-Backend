package welkit_server.global.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessMessage {
    /*
    200 OK
     */
    LOGIN_SUCCESS_COMPANY(HttpStatus.OK.value(), "회사 메일 계정으로 로그인에 성공했습니다."),
    LOGIN_SUCCESS_PERSONAL(HttpStatus.OK.value(), "개인 메일 계정으로 로그인에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK.value(), "로그아웃 되었습니다."),

    COMPANY_EMAIL_SEND_SUCCESS(HttpStatus.OK.value(), "회사 메일 인증 코드 전송이 완료되었습니다."),
    PERSONAL_EMAIL_SEND_SUCCESS(HttpStatus.OK.value(), "개인 메일 인증 코드 전송이 완료되었습니다."),
    COMPANY_EMAIL_VERIFICATION_SUCCESS(HttpStatus.CREATED.value(), "회사 메일 인증이 완료되었습니다."),
    PERSONAL_EMAIL_VERIFICATION_SUCCESS(HttpStatus.CREATED.value(), "개인 메일 인증이 완료되었습니다."),

    LOAD_SUCCESS(HttpStatus.OK.value(),"조회가 완료되었습니다"),
    UPDATED_SUCCESS(HttpStatus.OK.value(), "수정이 완료되었습니다"),

    /*
    201 Created
     */
    CREATED_SUCCESS(HttpStatus.CREATED.value(), "등록 완료되었습니다."),

    COMPANY_EMAIL_REGISTER_SUCCESS(HttpStatus.CREATED.value(), "회사 메일 회원가입이 완료되었습니다."),
    PERSONAL_EMAIL_REGISTER_SUCCESS(HttpStatus.CREATED.value(), "개인 메일 회원가입이 완료되었습니다."),
    GOOGLE_COMPANY_REGISTER_SUCCESS(HttpStatus.CREATED.value(), "구글 로그인을 통한 회사 인증 회원가입이 완료되었습니다."),

    /*
    204 No Content
     */
    DELETED_SUCCESS(HttpStatus.NO_CONTENT.value(), "삭제가 완료되었습니다");
    ;

    private final int status;
    private final String message;
}
