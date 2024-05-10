package project.shop1.feature.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.feature.main.dto.GetProductsByRatingRequestDto;
import project.shop1.feature.main.dto.GetProductsByRatingResponseDto;
import project.shop1.feature.main.service.MainService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    /* 메인 페이지 평점 순 상품 노출 */
    @PostMapping("/main/get-products-by-rating") //GetProductsByRatingRequestDto : int page
    public List<GetProductsByRatingResponseDto> getProductsByRating(@RequestBody GetProductsByRatingRequestDto getProductsByRatingRequestDto) {
        List<GetProductsByRatingResponseDto> result = mainService.getProductsByRating(getProductsByRatingRequestDto);

        return result;
    }
}
