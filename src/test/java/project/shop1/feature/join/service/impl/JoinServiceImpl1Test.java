package project.shop1.feature.join.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.service.UserService;
import project.shop1.entity.UserEntity;
import project.shop1.feature.join.repository.JoinRepository;
import project.shop1.feature.join.service.JoinService;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JoinServiceImpl1Test {

    @Autowired JoinService joinService;

    @Autowired
    JoinRepository joinRepository;
    
    @Test
    public void 회원가입() throws Exception{
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setName("kim");

        
        //when
        
        //then
    }
    
    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        
        //when
        
        //then
    }

}