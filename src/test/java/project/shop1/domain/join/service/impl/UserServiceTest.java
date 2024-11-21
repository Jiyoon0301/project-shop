package project.shop1.domain.join.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shop1.domain.emailAuth.service.EmailAuthService;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.dto.JoinRequestDto;
import project.shop1.domain.user.service.UserService;
import project.shop1.domain.user.service.impl.UserServiceImpl;

import javax.naming.Name;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class) //Junit5
public class UserServiceTest {

    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String PHONE = "phoneNumber";
    private static final String EMAIL = "email";

    @Mock
    private UserRepository userRepository;// Mock 리포지토리

    @Mock
    private EmailAuthService emailAuthService;

    @InjectMocks
    private UserServiceImpl userService; // 테스트 대상 클래스
//    private UserServiceImpl userService = null; // 테스트 대상 클래스

    @Test
    void 회원가입_요청이_유효하면_회원이_저장된다() {
        //given
        JoinRequestDto dto = new JoinRequestDto(ACCOUNT, PASSWORD, NAME, PHONE, EMAIL);

        UserEntity savedUser = new UserEntity(ACCOUNT, PASSWORD, NAME, PHONE, EMAIL);

        when(userRepository.findByAccount(ACCOUNT)).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        //when
        userService.join(dto);

        //then
        verify(userRepository, times(1)).save(any(UserEntity.class));  // 회원이 한 번 저장되었는지 확인}
    }
}