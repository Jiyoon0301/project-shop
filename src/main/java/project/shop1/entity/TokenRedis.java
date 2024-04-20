package project.shop1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash(value = "jwtToken", timeToLive = 60 * 60 * 24) //refreshToken 과 expireTime 일치
public class TokenRedis {

    @Id
    private String id; //account

    @Indexed
    private String accessToken;

    private String refreshToken;
}
