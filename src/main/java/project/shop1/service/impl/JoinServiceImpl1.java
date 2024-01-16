package project.shop1.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shop1.dto.JoinRequestDto;
import project.shop1.entity.UserEntity;
import project.shop1.common.enums.Rank;
import project.shop1.repository.JoinRepository;
import project.shop1.service.JoinService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinServiceImpl1 implements JoinService {

    private final JoinRepository joinRepository;


    @Override
    @Transactional
    public void joinUser(JoinRequestDto joinRequestDto) throws Exception {

        String userId = joinRequestDto.getUserId();
        String password=joinRequestDto.getPassword();

        String name = joinRequestDto.getName();
        String phoneNumber= joinRequestDto.getPhoneNumber();
        String email = joinRequestDto.getEmail();

        Optional<UserEntity> findUserEntity = joinRepository.findUserEntityByName(name);

        if(findUserEntity.isPresent()){ //정보가 들어온 회원과 중복되는 회원이 있을 때
            throw new Exception("중복되는 회원이 존재합니다."); //예외처리
        }

        //중복되는 회원이 없을 때
        UserEntity userEntity = UserEntity.builder() // ==setId,password... ->
                .userId(userId)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .rank(Rank.MEMBER)
                .build();

        joinRepository.saveUserEntity(userEntity);

    }
}
