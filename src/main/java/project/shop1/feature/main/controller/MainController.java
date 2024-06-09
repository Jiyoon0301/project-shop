package project.shop1.feature.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.feature.main.dto.GetProductsByRankingRequestDto;
import project.shop1.feature.main.dto.GetProductsByRankingResponseDto;
import project.shop1.feature.main.service.MainService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    /* 메인 페이지 판매 순 상품 노출
    * GetProductsByRankingRequestDto :
    * int page
    */
    @PostMapping("/main/get-products-by-ranking")
    public List<GetProductsByRankingResponseDto> getProductsByRanking(@RequestBody GetProductsByRankingRequestDto getProductsByRankingRequestDto) {
        List<GetProductsByRankingResponseDto> result = mainService.getProductsByRanking(getProductsByRankingRequestDto);

        return result;
    }
}
