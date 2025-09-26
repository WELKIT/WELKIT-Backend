package welkit_server.domain.mail.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import welkit_server.domain.mail.dto.request.EmailPostRequest;
import welkit_server.domain.mail.dto.request.EmailVerifyRequest;
import welkit_server.domain.mail.dto.response.EmailMessageResponse;
import welkit_server.domain.mail.model.EmailCodePurpose;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.redis.RedisKey;
import welkit_server.global.redis.RedisUtil;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisUtil redisUtil;

    public void sendVerificationEmail(String email, EmailCodePurpose purpose) {
        String code = createCode();
        sendMail(email, code, purpose);
        redisUtil.saveEmailCode(email,code,purpose);
    }

    private void sendMail(String email, String code, EmailCodePurpose purpose) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            mimeMessage.setRecipients(Message.RecipientType.TO, email);
            mimeMessage.setSubject("[welkit] " + getSubjectByPurpose(purpose));
            mimeMessage.setText("인증번호: " + code, "utf-8");
            mimeMessage.setFrom("no-reply@welkit.kr");

            mailSender.send(mimeMessage);
            log.info("메일 전송 완료 - email: {}, purpose: {}, code: {}", email, purpose, code);

        } catch (MessagingException e) {
            log.error("메일 전송 실패 - email: {}", email, e);
            throw new RuntimeException("메일 전송 실패");
        }
    }

    private String getSubjectByPurpose(EmailCodePurpose purpose) {
        return switch (purpose) {
            case PASSWORD_RESET -> "비밀번호 재설정을 위한 인증 코드 발송";
            case SIGN_UP -> "회원가입 이메일 인증 코드 발송";
            case CHANGE_EMAIL -> "이메일 변경 인증 코드 발송";
        };
    }

    public void verifyEmail(String email, String inputCode, String type) {
        String emailCode = redisUtil.getEmailCode(email);

        if (emailCode == null) {
            log.warn("{} 이메일 인증 코드 없음 또는 만료됨 - email: {}", type, email);
            throw new BadRequestException(ErrorMessage.EXPIRED_EMAIL_CODE); // 코드 만료
        }

        if (!emailCode.equals(inputCode.trim())) {
            log.warn("{} 이메일 인증 코드 불일치 - email: {}, 입력된 코드: {}", type, email, inputCode);
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_CODE); // 코드 불일치
        }

        try {
            redisUtil.saveVerifiedEmail(email);
            redisUtil.deleteEmailCode(email);
        } catch (Exception e) {
            log.error("이메일 인증 실패 - email: {}", email, e);
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION); // 시스템 오류
        }

        log.info("{} 이메일 인증 성공 - email: {}", type, email);
    }

    public void resendVerificationEmail(String email,EmailCodePurpose purpose) {
        EmailMessageResponse emailMessageResponse = EmailMessageResponse.builder()
                .to(email)
                .subject("[welkit] 이메일 재인증을 위한 인증 코드 발송")
                .build();

        //String code = sendMail(emailMessageResponse, "email",purpose);
        //redisUtil.saveEmailCode(email, code, purpose);
    }

    public void resendEmail(EmailPostRequest emailPostRequest,EmailCodePurpose purpose) {

        String email = emailPostRequest.getEmail();
        String key = RedisKey.EMAIL_CODE.getKey(email);

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }

        resendVerificationEmail(email,purpose);
    }

    public void verifyResendVerificationEmail(EmailVerifyRequest emailVerifyRequest) {
        String email = emailVerifyRequest.getEmail();
        String emailCode = redisUtil.getEmailCode(email);
        String inputCode = emailVerifyRequest.getCode();

        if (emailCode == null) {
            throw new BadRequestException(ErrorMessage.EXPIRED_EMAIL_CODE); // 코드 만료
        }

        if (!emailCode.equals(inputCode.trim())) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_CODE); // 코드 불일치
        }
        try {
            redisUtil.saveVerifiedEmail(email);
            redisUtil.deleteEmailCode(email);
        } catch (Exception e) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION); // 시스템 오류
        }
    }

    public String createCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            key.append(random.nextInt(10)); // 0 - 9
        }
        return key.toString();
    }

}
