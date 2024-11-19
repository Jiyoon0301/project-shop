package project.shop1.feature.searchUserTest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.UserEntity;
import project.shop1.feature.searchUserTest.dto.SearchUserRequestDto;
import project.shop1.feature.searchUserTest.dto.SearchUserResponseDto;
import project.shop1.feature.searchUserTest.repository.SearchUserRepository;
import project.shop1.feature.searchUserTest.service.SearchUserService;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchUserServiceImpl implements SearchUserService {

    private final SearchUserRepository searchUserRepository;

    @Override
    @Cacheable(value = "userCache", key = "#searchUserRequestDto.account")
    public SearchUserResponseDto searchUser(SearchUserRequestDto searchUserRequestDto){

        String account = searchUserRequestDto.getAccount();

        UserEntity userEntity = searchUserRepository.findByAccount(account);
        SearchUserResponseDto result = new SearchUserResponseDto(userEntity.getName());

        return result;
    }

}
