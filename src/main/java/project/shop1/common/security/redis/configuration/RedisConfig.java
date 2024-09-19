package project.shop1.common.security.redis.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import project.shop1.common.security.jwt.dto.JwtToken;

import java.util.Date;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    /* Redis 연결 팩토리 설정 */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){

        return new LettuceConnectionFactory(host, port);
    }

    /* Redis 캐싱을 사용하기 위한 캐시 매니저 설정 */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    /* JWT Refresh Token 생성 메서드 */
    public static String createRefreshToken(String key){
        Claims claims = Jwts.claims();

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3)) //유효시간 (3일)
                .signWith(SignatureAlgorithm.HS256, key) //HS256 알고리즘으로 key를 암호화
                .compact();
    }


}
