package project.shop1.global.security.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@RedisHash(value = "jwtToken", timeToLive = 60*60*24*3) //=refresh Token의 만료시간
public class RefreshToken { //redis 에 저장할 객체

    @Id
    private String account;

    private String refreshToken;

    @Indexed //해당 필드 값으로 데이터를 찾아올 수 있음
    private String accessToken;
}
