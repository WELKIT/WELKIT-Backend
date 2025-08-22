package welkit_server.global.redis;

import jakarta.annotation.PostConstruct;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void init() {
        log.info("RedisUtil 초기화 : RedisTemplate = {}", redisTemplate);
    }

    public void saveEmailCode(String email, String code) {
        log.info("Redis 저장 시도: email = {}, code = {}", email, code);
        redisTemplate.opsForValue().set(RedisKey.EMAIL_CODE.getKey(email), code, RedisKey.EMAIL_CODE.getTtl());
    }

    public String getEmailCode(String email) {
        return redisTemplate.opsForValue().get(RedisKey.EMAIL_CODE.getKey(email));
    }

    public void deleteEmailCode(String email) {
        redisTemplate.delete(RedisKey.EMAIL_CODE.getKey(email));
    }

    public void saveVerifiedEmail(String email) {
        redisTemplate.opsForValue().set(RedisKey.VERIFIED_EMAIL.getKey(email), "true" , RedisKey.VERIFIED_EMAIL.getTtl());
    }

    public boolean isVerifiedEmail(String email) {
        String result = redisTemplate.opsForValue().get(RedisKey.VERIFIED_EMAIL.getKey(email));
        return "true".equals(result);
    }

}
