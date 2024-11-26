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
     *
     * @param reviewRequestDto
     */
    @Override
    @Transactional
    public void createReview(ReviewRequestDto reviewRequestDto) {
        Book book = productRepository.findById(reviewRequestDto.getProductId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "유효하지 않은 상품 ID입니다."));

        UserEntity userEntity = userRepository.findById(reviewRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다"));

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
    public Page<GetReviewsResponseDto> getReviewsByProduct(Long productId, GetReviewsRequestDto getReviewsRequestDto) {
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
        // 사용자 확인 (존재하지 않으면 예외 처리)
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));

        // 리뷰 조회
        List<Review> reviews = reviewRepository.findByUser(userEntity);

        // 3. 엔티티를 DTO로 변환
        return reviews.stream()
                .map(review -> GetReviewsResponseDto.builder()
                        .id(review.getId())
                        .content(review.getContent())
                        .rating(review.getRating())
                        .userName(userEntity.getName()) // 이미 로드된 User 객체 사용
                        .regDate(review.getRegDate().toString())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateReview(Long reviewId, ReviewRequestDto reviewRequestDto) {
        // 리뷰 엔티티 조회
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 리뷰입니다."));

        // 리뷰 작성자가 요청한 사용자와 동일한지 확인 (권한 체크)
        UserEntity currentUser = getCurrentAuthenticatedUser(); // 현재 인증된 사용자 가져오기
        if (!review.getUserEntity().equals(currentUser)) {
            throw new SecurityException("해당 리뷰 수정 접근 권한이 없습니다.");
        }

        // 새로운 데이터로 업데이트
        review.setContent(reviewRequestDto.getContent());
        review.setRating(reviewRequestDto.getRating());

        // Product 객체가 변경되는 경우 (옵션)
        if (reviewRequestDto.getProductId() != null) {
            Book book = productRepository.findById(reviewRequestDto.getProductId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 상품입니다."));
            review.setBook(book);
        }

        // 저장 (Transaction에 의해 자동 저장됨)
        reviewRepository.save(review);
    }

    private UserEntity getCurrentAuthenticatedUser() {
        String userName = SecurityUtils.getCurrentUsername();
        return userRepository.findByName(userName)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."));
    }

    @Transactional
    @Override
    public void deleteReview(Long reviewId) {
        // 리뷰 엔티티 조회
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 리뷰입니다."));

        // 리뷰 작성자가 요청한 사용자와 동일한지 확인 (권한 체크)
        UserEntity currentUser = getCurrentAuthenticatedUser(); // 현재 인증된 사용자 가져오기
        if (!review.getUserEntity().equals(currentUser)) {
            throw new SecurityException("해당 리뷰 삭제 권한이 없습니다.");
        }

        // 리뷰 삭제
        reviewRepository.delete(review);
    }

    @Override
    public double getAverageRating(Long productId) {
        Double averageRating = reviewRepository.findAverageRatingByProductId(productId);
        if (averageRating != null) {
            return averageRating;
        }
        return 0.0;
    }
}
