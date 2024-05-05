package project.shop1.feature.login.naver.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.repository.UserRepository;
import project.shop1.common.security.jwt.JwtTokenProvider;
import project.shop1.feature.login.naver.service.NaverLoginService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NaverLoginServiceImpl implements NaverLoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
}
