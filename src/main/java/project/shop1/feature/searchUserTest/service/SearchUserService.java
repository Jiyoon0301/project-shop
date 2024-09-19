package project.shop1.feature.searchUserTest.service;

import project.shop1.feature.searchUserTest.dto.SearchUserRequestDto;
import project.shop1.feature.searchUserTest.dto.SearchUserResponseDto;

public interface SearchUserService {

    SearchUserResponseDto searchUser(SearchUserRequestDto searchUserRequestDto);

    }
