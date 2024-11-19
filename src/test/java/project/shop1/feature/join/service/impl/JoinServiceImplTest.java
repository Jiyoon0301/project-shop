package project.shop1.feature.join.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.UserEntity;
import project.shop1.feature.join.dto.JoinRequestDto;
import project.shop1.feature.join.repository.JoinRepository;
import project.shop1.feature.join.service.JoinService;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class) //Junit5
@SpringBootTest
@Transactional
public class JoinServiceImplTest {

    @Autowired
    JoinService joinService; // 스프링의 의존성 주입 컨테이너가 구현체를 자동으로 주입

    @Autowired
    JoinRepository joinRepository;

//    @Test
//    void 회원가입() throws Exception{
//        //given
//        JoinRequestDto dto = new JoinRequestDto("account", "password", "name", "phoneNumber", "email");
//        String expected = "name";
//
//        //when
//        joinService.join(dto);
//        UserEntity userEntity = joinRepository.findUserEntityByAccount("account").get();
//        String result = userEntity.getName();
//
//        //then
//        assertThat(result).isEqualTo(expected);
//    }

//    @Test
//    public void 중복_회원_예외() throws Exception{
//        //given
//
//        //when
//
//        //then
//    }

}