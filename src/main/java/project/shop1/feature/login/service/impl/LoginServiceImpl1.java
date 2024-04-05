package project.shop1.feature.login.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.common.repository.UserRepository;
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
    private final UserRepository userRepository;

//    @Autowired
//    private final PasswordEncoder passwordEncoder;
    @Override
    public void loginUser(LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest) {
        String account=loginRequestDto.getAccount();
        String password=loginRequestDto.getPassword();

        Optional<UserEntity> userEntityByAccount = userRepository.findUserEntityByAccount(account);

        if (userEntityByAccount.isEmpty()){ //일치하는 회원이 없을 때
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 아이디입니다.");
        }

        System.out.println(userEntityByAccount.get());
        if(!userEntityByAccount.get().getPassword().equals(password)){
            throw new BusinessException(ErrorCode.INVALID_PASSWORD, "비밀번호가 일치하지 않습니다.");
        }

        /* 비밀번호 인코딩 */
//        if(!passwordEncoder.matches(password, userEntity.getPassword())){
//            throw new NotExistUserEntity("비밀번호가 일치하지 않습니다.");
//        }

        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("account", account);
//        httpSession.setMaxInactiveInterval(); //로그인 유지 기간

    }
}
