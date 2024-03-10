package project.shop1.SpringSecurity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {

    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000*60*60*24;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000*60*60*24;

//    private final JwtTokenProvider jwtTokenProvider;



}
