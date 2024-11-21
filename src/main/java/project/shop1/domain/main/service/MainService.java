package project.shop1.domain.main.service;

import project.shop1.domain.main.dto.GetProductsByRankingRequestDto;
import project.shop1.domain.main.dto.GetProductsByRankingResponseDto;

import java.util.List;

public interface MainService {

    /* 평점 순 상품 노출 */
    List<GetProductsByRankingResponseDto> getProductsByRanking(GetProductsByRankingRequestDto getProductsByRankingRequestDto);

    }
