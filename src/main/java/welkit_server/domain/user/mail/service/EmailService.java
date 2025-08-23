package welkit_server.domain.user.mail.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import welkit_server.domain.user.mail.dto.response.EmailMessageResponse;
import welkit_server.global.redis.RedisKey;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;

    public String sendMail(EmailMessageResponse emailMessage, String type){
        String authNum = createCode();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try{
            if (type.equals("email")) {
                mimeMessage.setRecipients(Message.RecipientType.TO, emailMessage.getTo());
                mimeMessage.setSubject(emailMessage.getSubject());
                mimeMessage.setText("인증번호: " + authNum, "utf-8");
                mailSender.send(mimeMessage);
                redisTemplate.opsForValue().set(RedisKey.EMAIL_CODE.getKey(emailMessage.getTo()), authNum, RedisKey.EMAIL_CODE.getTtl() );
            }
        }catch (MessagingException e){
            log.error("메일 전송 실패", e);
            throw new RuntimeException("메일 전송 실패");
        }
        return authNum;
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
