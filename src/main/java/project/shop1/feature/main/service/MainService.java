package project.shop1.feature.main.service;

import project.shop1.feature.main.dto.GetProductsByRatingRequestDto;
import project.shop1.feature.main.dto.GetProductsByRatingResponseDto;

import java.util.List;

public interface MainService {

    /* 평점 순 상품 노출 */
    List<GetProductsByRatingResponseDto> getProductsByRating(GetProductsByRatingRequestDto getProductsByRatingRequestDto);

    }
