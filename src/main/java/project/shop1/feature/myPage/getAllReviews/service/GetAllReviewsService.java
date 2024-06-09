package project.shop1.feature.myPage.getAllReviews.service;

import project.shop1.feature.myPage.getAllReviews.dto.GetAllReviewsResponseDto;

import java.util.List;

public interface GetAllReviewsService {

    // 회원의 리뷰 전체 검색
    List<GetAllReviewsResponseDto> getAllReviews();

    }
