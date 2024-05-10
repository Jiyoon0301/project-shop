package project.shop1.feature.logout.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.security.jwt.JwtTokenProvider;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LogoutService {

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void logout (String accessToken, String account){
        //레디스에 accessToken 사용하지 못하도록 등록
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
    }



}
