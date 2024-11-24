package project.shop1.domain.join.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shop1.common.exception.BusinessException;
import project.shop1.domain.user.dto.GetUserResponseDto;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.domain.user.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

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

    @Test
    void 존재하지_않는_사용자를_조회하면_예외가_발생한다() {
        // given
        Long id = 1L;

        // when & then
        assertThatThrownBy(() -> userService.findUserById(id))
                .isInstanceOf(BusinessException.class)
                .hasMessage("사용자를 찾을 수 없습니다.");
    }

    @Test
    void 존재하는_사용자를_삭제한다() {
        // given
        Long id = 1L;
        UserEntity userEntity = new UserEntity("account", "password", "name", "email", "phone");
        userEntity.setId(id);  // 수동으로 id 설정 (Mock 객체이므로 @GeneratedValue여도 가능)

        given(userRepository.findById(id)).willReturn(Optional.of(userEntity));

        // when
        userService.deleteUser(id);

        // then
        then(userRepository).should(times(1)).delete(userEntity); // userRepository.delete() 메서드가 한 번 호출되었는지 확인
    }

    @Test
    void deleteUser_존재하지_않는_사용자를_삭제하려고_하면_예외가_발생한다() {
        // given
        Long id = 1L;
        given(userRepository.findById(id)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.deleteUser(id))
                .isInstanceOf(BusinessException.class)
                .hasMessage("사용자를 찾을 수 없습니다.");

        then(userRepository).should(never()).delete(any()); // delete()메서드가 호출되지 않았는지 검증
    }
}

