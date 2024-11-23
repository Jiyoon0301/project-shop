package project.shop1.domain.join.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.domain.user.service.impl.JoinServiceImpl;

@ExtendWith(SpringExtension.class) //Junit5
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;// Mock 리포지토리

    @InjectMocks
    private JoinServiceImpl joinService; // 테스트 대상 클래스

    @Test
    void 사용자_아이디로_사용자를_조회한다() {
        // given
        
        // when
        
        // then
    }
}
