package welkit_server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.mail.dto.request.EmailPostRequest;
import welkit_server.domain.mail.dto.request.EmailVerifyRequest;
import welkit_server.domain.mail.service.EmailService;
import welkit_server.domain.user.dto.request.ResetPasswordRequest;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.exception.model.NotFoundException;
import welkit_server.global.redis.RedisUtil;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;
    private final EmailService emailService;

    public void sendEmail(EmailPostRequest emailPostRequest) {
        emailService.resendEmail(emailPostRequest);
    }

    public void verifyEmail(EmailVerifyRequest emailVerifyRequest) {
        emailService.verifyResendVerificationEmail(emailVerifyRequest);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String email = resetPasswordRequest.getEmail();

        if(!redisUtil.isVerifiedEmail(email)) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow( () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

        String encodedPassword = passwordEncoder.encode(resetPasswordRequest.getPassword());
        user.resetPassword(encodedPassword);
    }

}
