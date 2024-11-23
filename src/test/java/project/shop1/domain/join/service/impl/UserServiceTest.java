package project.shop1.domain.join.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shop1.domain.user.dto.GetUserResponseDto;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.domain.user.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class) //Junit5
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;// Mock 리포지토리

    @InjectMocks
    private UserServiceImpl userService; // 테스트 대상 클래스

    @Test
    void 사용자_아이디로_사용자를_조회한다() {
        // given
        Long id = 1L;
        UserEntity userEntity = new UserEntity("account", "password", "name", "email", "phone");
        userEntity.setId(id);  // 수동으로 id 설정 (Mock 객체이므로 @GeneratedValue여도 가능)

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity)); // findById(id) 호출 시 userEntity를 반환하도록 설정

        // when
        GetUserResponseDto responseDto = userService.findUserById(id);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getId()).isEqualTo(id);  // id가 일치하는지 확인
    }
}
