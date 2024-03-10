package project.shop1.feature.login.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.UserEntity;
import project.shop1.feature.login.dto.LoginRequestDto;
import project.shop1.feature.login.exception.NotExistUserEntity;
import project.shop1.feature.login.repository.LoginRepository;
import project.shop1.feature.login.service.LoginService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginServiceImpl1 implements LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Override
    public void loginUser(LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest) {
        String userId=loginRequestDto.getUserId();
        String password=loginRequestDto.getPassword();

        Optional<UserEntity> userEntityByUserId = loginRepository.findUserEntityByUserId(userId);

        if (userEntityByUserId.isEmpty()){ //일치하는 회원이 없을 때
            throw new NotExistUserEntity("존재하지 않는 회원입니다.");
        }

        UserEntity userEntity = userEntityByUserId.get();

        if(!passwordEncoder.matches(password, userEntity.getPassword())){
            throw new NotExistUserEntity("비밀번호가 일치하지 않습니다.");
        }

        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("userId",userId);
//        httpSession.setMaxInactiveInterval(); //로그인 유지 기간

    }
}
