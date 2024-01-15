package project.shop1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.dto.JoinRequestDto;
import project.shop1.service.JoinService;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String joinUser(JoinRequestDto joinRequestDto) throws Exception{
        joinService.joinUser(joinRequestDto);
        return "ok";
    }
}
