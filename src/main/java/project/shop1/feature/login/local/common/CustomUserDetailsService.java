package project.shop1.feature.login.local.common;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.repository.UserRepository;
import project.shop1.entity.UserEntity;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException{

        return userRepository.findUserEntityByAccount(account)
                .map(this::createUserDetails)
                .orElseThrow(()-> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
    }

    //해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    //name = account 로 설정
    private UserDetails createUserDetails(UserEntity userEntity){
        return User.builder()
                .username(userEntity.getAccount())
                .password(userEntity.getPassword())
                .roles(userEntity.getRoles().toArray(new String[0]))
                .build();
    }

}
