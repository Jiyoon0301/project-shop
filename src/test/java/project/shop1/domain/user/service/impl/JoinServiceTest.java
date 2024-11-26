package project.shop1.domain.user.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shop1.global.exception.BusinessException;
import project.shop1.domain.auth.emailAuth_needRefactor.service.EmailAuthService;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.dto.JoinRequestDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class) //Junit5
public class JoinServiceTest {

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
    private JoinServiceImpl joinService; // 테스트 대상 클래스

    @Test
    void 회원가입_요청이_유효하면_회원이_저장된다() {
        //given
        JoinRequestDto dto = new JoinRequestDto(ACCOUNT, PASSWORD, NAME, PHONE, EMAIL);

        UserEntity savedUser = new UserEntity(ACCOUNT, PASSWORD, NAME, PHONE, EMAIL);

        when(userRepository.findByAccount(ACCOUNT)).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        //when
        joinService.join(dto);

        //then
        verify(userRepository, times(1)).save(any(UserEntity.class));  // 회원이 한 번 저장되었는지 확인}
    }

    @Test
    void 중복된_아이디로_회원가입을_시도하면_예외가_발생한다() {
        //given
        JoinRequestDto joinRequestDto = new JoinRequestDto(ACCOUNT, PASSWORD, NAME, PHONE, EMAIL);
        when(userRepository.findByAccount(ACCOUNT)).thenReturn(Optional.of(new UserEntity()));

        //when & then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            joinService.join(joinRequestDto);  // 중복된 아이디로 회원가입 시도
        });

        // 예외 메시지 검증
        assertEquals("이미 존재하는 아이디입니다.", exception.getMessage());
    }
}