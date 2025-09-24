package welkit_server.global.redis;

import lombok.Getter;
import java.time.Duration;

@Getter
public enum RedisKey {

    EMAIL_CODE("emailCode:", Duration.ofMinutes(3)),
    VERIFIED_EMAIL("verified:", Duration.ofMinutes(10));

    private final String prefix;
    private final Duration ttl;

    RedisKey(String prefix, Duration ttl) {
        this.prefix = prefix;
        this.ttl = ttl;
    }

    public String getKey(String value) {
        return prefix + value;
    }

}
