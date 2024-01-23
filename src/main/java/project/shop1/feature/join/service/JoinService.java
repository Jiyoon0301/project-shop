package project.shop1.feature.join.service;

import project.shop1.feature.join.dto.JoinRequestDto;

public interface JoinService {

    void joinUser(JoinRequestDto joinRequestDto) throws Exception;

}
