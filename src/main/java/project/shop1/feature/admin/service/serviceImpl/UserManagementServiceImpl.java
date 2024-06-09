package project.shop1.feature.admin.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.UserEntity;
import project.shop1.feature.admin.dto.userDto.AdminSearchUserByAccountRequestDto;
import project.shop1.feature.admin.repository.UserManagementRepository;
import project.shop1.feature.admin.service.UserManagementService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserManagementServiceImpl implements UserManagementService { //핵심 비즈니스 로직 구현

    private final UserManagementRepository userManagementRepository;


    /* 회원 관리 */
//    @Override
//    public Optional<Author> authorGetDetail(String authorName){
//        Optional<Author> result = productRepository.findAuthorByName(authorName);
//        return Optional.ofNullable(result);
//    }

    /* 회원 관리 - account 로 회원 찾기*/
    @Override
    public UserEntity adminSearchUserByAccount(AdminSearchUserByAccountRequestDto adminSearchUserByAccountRequestDto){
        String account = adminSearchUserByAccountRequestDto.getAccount();
        UserEntity result = userManagementRepository.findUserEntityByAccount(account).get();
        return result;
    }

}
