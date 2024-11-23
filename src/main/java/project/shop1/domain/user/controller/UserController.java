package project.shop1.domain.user.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.domain.user.dto.GetUserResponseDto;
import project.shop1.domain.user.dto.JoinRequestDto;
import project.shop1.domain.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserService joinService;

    public UserController(
            @Qualifier("userServiceImpl") UserService userService,
            @Qualifier("joinServiceImpl") UserService joinService) {
        this.userService = userService;
        this.joinService = joinService;
    }

    // 회원가입
    @PostMapping("/join") // String account, String password, String name, String phoneNumber, String email
    @PreAuthorize("permitAll()")
    public ResponseEntity<BooleanResponse> join(@Validated(value = ValidationSequence.class) @RequestBody JoinRequestDto joinRequestDto) {
        joinService.join(joinRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponseDto> getUserById(@PathVariable Long id) {
        GetUserResponseDto getUserResponseDto = userService.findUserById(id);
        return ResponseEntity.ok(getUserResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BooleanResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }
}

