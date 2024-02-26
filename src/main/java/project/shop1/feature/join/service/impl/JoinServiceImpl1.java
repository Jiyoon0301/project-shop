package project.shop1.feature.join.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shop1.common.exception.InvalidEmailFormatException;
import project.shop1.common.exception.UserAlreadyExistsException;
import project.shop1.feature.join.dto.JoinRequestDto;
import project.shop1.entity.UserEntity;
import project.shop1.common.enums.Rank;
import project.shop1.feature.join.repository.JoinRepository;
import project.shop1.feature.join.service.JoinService;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Optional;

@Service
@RequiredArgsConstructor //final이 있는 필드만 가지고 생성자 만들어줌
@Transactional(readOnly = true)
public class JoinServiceImpl1 implements JoinService {

    private final JoinRepository joinRepository;

    @Override
    @Transactional
    public void joinUser(JoinRequestDto joinRequestDto) throws UserAlreadyExistsException, InvalidEmailFormatException {

        String userId = joinRequestDto.getUserId();
        String password=joinRequestDto.getPassword();

        String name = joinRequestDto.getName();
        String phoneNumber= joinRequestDto.getPhoneNumber();
        String email = joinRequestDto.getEmail();

        Optional<UserEntity> findUserEntity = joinRepository.findUserEntityByUserId(userId);


        // userId 중복 검사
        if(findUserEntity.isPresent()){
            throw new UserAlreadyExistsException();
        }

        //이메일 형식 검사
        if (!mailFormCheck(email)){
            throw new InvalidEmailFormatException();
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

    @Override
    public Boolean verificationMail(JoinRequestDto joinRequestDto, String userAuthNum){ //인증번호가 일치하는지 검증

        if (joinRequestDto.getAuthCode().equals(userAuthNum)){
            return true;
        } else return false;

    }

    /* 이메일 형식 검사 */
    @Override
    public Boolean mailFormCheck(String email) {
        String form = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(form);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
