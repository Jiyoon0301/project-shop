package project.shop1.service;

import org.springframework.stereotype.Service;
import project.shop1.dto.JoinRequestDto;

public interface JoinService {

    void joinUser(JoinRequestDto joinRequestDto) throws Exception;

}
