package project.shop1.feature.join.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.UserEntity;
import project.shop1.feature.join.dto.JoinRequestDto;
import project.shop1.feature.join.repository.JoinRepository;
import project.shop1.feature.join.service.JoinService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class) //Junit5
@SpringBootTest
@Transactional
public class JoinServiceTest {

    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String PHONE = "phoneNumber";
    private static final String EMAIL = "email";

    @MockBean
    private JoinRepository joinRepository; // Mock 리포지토리

    @Autowired
    private JoinService joinService; // 테스트 대상 클래스

    public JoinServiceTest() {
        MockitoAnnotations.openMocks(this); // Mock 객체 초기화
    }

    @Test
    void 회원_저장_테스트() {
        // given
        JoinRequestDto dto = new JoinRequestDto(ACCOUNT, PASSWORD, NAME, PHONE, EMAIL);
        UserEntity userEntity = new UserEntity(ACCOUNT, PASSWORD, NAME, PHONE, EMAIL);

        when(joinRepository.saveUserEntity(any(UserEntity.class))).thenReturn(userEntity); // 저장 동작 Mock
        when(joinRepository.findUserEntityByAccount(ACCOUNT)).thenReturn(Optional.of(userEntity)); // 조회 동작 Mock

        // when
        joinService.join(dto);

        // then
        verify(joinRepository, times(1)).saveUserEntity(any(UserEntity.class)); // 저장이 호출되었는지 검증
        Optional<UserEntity> savedUser = joinRepository.findUserEntityByAccount(ACCOUNT);
        assertThat(savedUser).isPresent(); // 저장된 사용자가 있는지 확인
        assertThat(savedUser.get().getName()).isEqualTo(NAME); // 이름 검증
        assertThat(savedUser.get().getEmail()).isEqualTo(EMAIL); // 이메일 검증
    }
}