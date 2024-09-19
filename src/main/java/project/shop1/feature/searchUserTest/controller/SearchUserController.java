package project.shop1.feature.searchUserTest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.feature.searchUserTest.dto.SearchUserRequestDto;
import project.shop1.feature.searchUserTest.dto.SearchUserResponseDto;
import project.shop1.feature.searchUserTest.service.SearchUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchUserController {

    private final SearchUserService searchUserService;

    /* account로 username 찾기 */
    @PostMapping("/search-username") //Dto : String account
    public SearchUserResponseDto getReviewByProductId(@RequestBody SearchUserRequestDto searchUserRequestDto) {
        SearchUserResponseDto result = searchUserService.searchUser(searchUserRequestDto);

        return result;
    }
}
