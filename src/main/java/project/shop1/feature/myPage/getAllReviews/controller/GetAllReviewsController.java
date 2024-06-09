package project.shop1.feature.myPage.getAllReviews.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.entity.Review;
import project.shop1.feature.main.dto.GetProductsByRankingRequestDto;
import project.shop1.feature.myPage.getAllReviews.dto.GetAllReviewsResponseDto;
import project.shop1.feature.myPage.getAllReviews.service.GetAllReviewsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetAllReviewsController {

    private final GetAllReviewsService getAllReviewsService;

    /* 해당 회원의 모든 리뷰 확인
    * GetAllReviewsResponseDto :
    * String bookTitle, String content, double rating, LocalDateTime regDate;
    */
    @PostMapping("/my-page/get-all-reviews")
    public List<GetAllReviewsResponseDto> getAllReviews() {
        List<GetAllReviewsResponseDto> result = getAllReviewsService.getAllReviews();

        return result;
    }
}
