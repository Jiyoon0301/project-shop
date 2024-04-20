package project.shop1.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import project.shop1.common.repository.UserRepository;
import project.shop1.common.repository.impl.UserRepositoryImpl;
import project.shop1.common.service.UserService;
import project.shop1.entity.UserEntity;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /* 로그인되어있는 회원 id 반환 */
    public Long getCurrentMember(Authentication authentication){
        UserEntity userEntity = userRepository.findUserEntityByAccount(authentication.getName()).get();
        return userEntity.getId();

    }
}
