package project.shop1.domain.review_needRefactor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.product.repository.ProductRepository;
import project.shop1.domain.review_needRefactor.repository.ReviewRepository;
import project.shop1.domain.review_needRefactor.service.ReviewService;
import project.shop1.domain.review_needRefactor.dto.*;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.global.security.SecurityUtils;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.review_needRefactor.entity.Review;
import project.shop1.domain.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * 리뷰 등록
     * @param reviewRequestDto
     */
    @Override
    @Transactional
    public void createReview(ReviewRequestDto reviewRequestDto){
        Book book = productRepository.findById(reviewRequestDto.getProductId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "유효하지 않은 상품 ID입니다."));

        UserEntity userEntity = userRepository.findById(reviewRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Review review = Review.builder()
                .userEntity(userEntity)
                .book(book)
                .content(reviewRequestDto.getContent())
                .rating(reviewRequestDto.getRating())
                .regDate(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
    }

    // 특정 상품에 대한 리뷰 조회
    @Override
    public Page<GetReviewsResponseDto> getReviewsByProduct(Long productId, GetReviewsRequestDto getReviewsRequestDto){
        Sort sort = Sort.by("regDate"); // 기본 정렬은 작성 날짜 기준
        if ("rating".equalsIgnoreCase(getReviewsRequestDto.getSortBy())) {
            sort = Sort.by("rating");
        }

        // 페이지네이션 정보 설정
        Pageable pageable = PageRequest.of(getReviewsRequestDto.getPage(), 10, sort);

        // JPA 페이지네이션 쿼리 실행
        Page<Review> reviewsPage = reviewRepository.findByProductIdAndRating(
                productId,
                getReviewsRequestDto.getRating(),
                pageable
        );

        // 엔티티 -> DTO 변환
        return reviewsPage.map(review -> GetReviewsResponseDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .rating(review.getRating())
                .userName(review.getUserEntity().getName())
                .regDate(review.getRegDate().toString())
                .build());
    }

    @Override
    public List<GetReviewsResponseDto> getReviewsByUser(Long userId) {
        return null;
    }

    @Override
    public void updateReview(Long reviewId, ReviewRequestDto reviewRequestDto) {

    }

    @Override
    public void deleteReview(Long reviewId) {

    }

    @Override
    public double getAverageRating(Long productId) {
        return 0;
    }

    /* 리뷰 수정 */
//    @Override
//    @Transactional
//    public void updateReview(UpdateReviewRequestDto updateReviewRequestDto){
//        String account = SecurityUtils.getCurrentUsername();
//
//        Long productId = updateReviewRequestDto.getProductId();
//        String content = updateReviewRequestDto.getContent();
//        double rating = updateReviewRequestDto.getRating();
//        LocalDateTime regDate = LocalDateTime.now();
//
//        reviewRepository.updateReview(productId, account, content, rating, regDate);
//
//        // 수정된 평점을 상품 평점평균에 반영
//        Book book = productRepository.findById(productId).get();
//        book.calAverageRating();
//
//    }
//
//    /* 리뷰 삭제 */
//    @Override
//    @Transactional
//    public void deleteReview(DeleteReviewRequestDto deleteReviewRequestDto){
//        String account = SecurityUtils.getCurrentUsername();
//        Long productId = deleteReviewRequestDto.getProductId();
//
//        Review review = reviewRepository.findReviewByProductIdAndUserEntityAccount(productId, account).get();
//        reviewRepository.deleteReview(review);
//
//        // 평점 평균 업데이트
//        Book book = productRepository.findById(productId).get();
//        book.getReviews().remove(review);
//        book.calAverageRating();
//    }
}
