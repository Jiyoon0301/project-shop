package project.shop1.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.domain.user.dto.request.JoinRequestDto;
import project.shop1.domain.user.dto.response.JoinResponseDto;
import project.shop1.domain.user.service.JoinService;
import project.shop1.global.util.validation.ValidationSequence;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class JoinController {

    private final JoinService joinService;

    /**
     * 회원가입
     *
     * @param joinRequestDto: String account, String password, String name, String phoneNumber, String email
     * @return
     */
    @PostMapping("/join")
    @PreAuthorize("permitAll()")
    public ResponseEntity<JoinResponseDto> join(@Validated(value = ValidationSequence.class) @RequestBody JoinRequestDto joinRequestDto) {
        JoinResponseDto joinResponseDto = joinService.join(joinRequestDto);
        return ResponseEntity.ok(joinResponseDto);
    }
}
