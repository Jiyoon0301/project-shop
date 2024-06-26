package project.shop1.feature.login.local.service.serviceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.common.repository.UserRepository;
import project.shop1.common.security.jwt.JwtTokenProvider;
import project.shop1.common.security.jwt.dto.JwtToken;
import project.shop1.common.security.redis.dto.RefreshToken;
import project.shop1.common.security.redis.repository.RefreshTokenRepository;
import project.shop1.entity.UserEntity;
import project.shop1.feature.login.local.dto.LocalLoginRequestDto;
import project.shop1.feature.login.local.service.LocalLoginService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocalLoginServiceImpl implements LocalLoginService {

    private final UserRepository userRepository;
    /* jwt */
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    /* redis */
    private final RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    /* Jwt 로그인 */
    @Override
    @Transactional
    public JwtToken login(LocalLoginRequestDto localLoginRequestDto){
        String account = localLoginRequestDto.getAccount();
        String password = localLoginRequestDto.getPassword();

        Optional<UserEntity> userEntity = userRepository.findUserEntityByAccount(account);
        if (userEntity.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 아이디입니다.");
        }

        if(!passwordEncoder.matches(password, userEntity.get().getPassword())){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "비밀번호가 일치하지 않습니다.");
        }

        //username(= account) + password 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, password);

        //실제 검증. authenticate() 메서드를 통해 요청된 회원에 대한 검증 진행
        //authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행 -> password 검증 matches 이용, 인코딩 필수
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //인증 정보를 기반으로 jWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        // 토큰을 redis 에 저장
        refreshTokenRepository.save(new RefreshToken(authentication.getName(), jwtToken.getRefreshToken(), jwtToken.getAccessToken())); //account, refreshToken, accessToken

        return jwtToken;
    }

    /* 세션 로그인 */
    @Override
    public void loginUser(LocalLoginRequestDto localLoginRequestDto, HttpServletRequest request) {
        String account = localLoginRequestDto.getAccount();
        String password = localLoginRequestDto.getPassword();

        Optional<UserEntity> userEntityByAccount = userRepository.findUserEntityByAccount(account);

        if (userEntityByAccount.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 아이디입니다.");
        }

        if(!passwordEncoder.matches(password, userEntityByAccount.get().getPassword())){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "비밀번호가 일치하지 않습니다.");
        }

        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("account", account);
//        httpSession.setMaxInactiveInterval(); //로그인 유지 기간

    }
}
