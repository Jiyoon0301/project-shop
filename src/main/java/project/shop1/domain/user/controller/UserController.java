package project.shop1.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.domain.user.dto.JoinRequestDto;
import project.shop1.domain.user.dto.SearchRequestDto;
import project.shop1.domain.user.dto.SearchResponseDto;
import project.shop1.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* 회원가입*/
    @PostMapping("/user/join") // String account, String password, String name, String phoneNumber, String email
    @PreAuthorize("permitAll()")
    public ResponseEntity<BooleanResponse> join(@Validated(value = ValidationSequence.class) @RequestBody JoinRequestDto joinRequestDto) {
        userService.join(joinRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* account 검색으로 username 찾기 */
    @PostMapping("/user/search-name") //Dto : String account
    public SearchResponseDto searchNameByAccount(@RequestBody SearchRequestDto searchRequestDto) {
        SearchResponseDto result = userService.searchByAccount(searchRequestDto);

        return result;
    }
}
