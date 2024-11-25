package project.shop1.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.shop1.global.util.reponse.BooleanResponse;
import project.shop1.domain.user.dto.GetUserResponseDto;
import project.shop1.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

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

