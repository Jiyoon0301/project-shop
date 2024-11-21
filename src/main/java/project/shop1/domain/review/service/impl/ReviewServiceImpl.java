package project.shop1.domain.review.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.common.repository.BookRepository;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.common.security.SecurityUtil;
import project.shop1.entity.Book;
import project.shop1.entity.Review;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.review.dto.*;
import project.shop1.domain.review.repository.ReviewRepository;
import project.shop1.domain.review.service.ReviewService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    /* 리뷰 등록 버튼 */
    @Override
    @Transactional
    public void registerReview(RegisterReviewRequestDto registerReviewRequestDto){
        String account = SecurityUtil.getCurrentUsername();
        UserEntity userEntity = userRepository.findByAccount(account).get();

        Long productId = registerReviewRequestDto.getProductId();
        Book book = bookRepository.findBookByBookId(productId).get();

        // 회원의 중복 리뷰 확인
        Optional<Review> checkDuplicateReview = reviewRepository.findReviewByProductIdAndUserEntityAccount(productId, account);
        if (checkDuplicateReview.isPresent()){
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT, "이미 등록된 리뷰가 존재합니다.");
        }

        String content = registerReviewRequestDto.getContent();
        double rating = registerReviewRequestDto.getRating();

        Review review = Review.builder()
                .userEntity(userEntity)
                .book(book)
                .content(content)
                .rating(rating)
                .regDate(LocalDateTime.now())
                .build();

        reviewRepository.saveReview(review);

        // 평점 평균 업데이트
        book.addReview(review);
        book.calAverageRating();
    }

    /* 상품에 대한 리뷰 조회 - productId로 */
    @Override
    public List<GetReviewByProductIdResponseDto> getReviewByProductId(GetReviewByProductIdRequestDto getReviewByProductIdRequestDto){

        Long productId = getReviewByProductIdRequestDto.getProductId();
        int page = getReviewByProductIdRequestDto.getPage();

        List<Review> reviews = reviewRepository.findReviewByProductId(productId, page);
        List<GetReviewByProductIdResponseDto> result = new ArrayList<>();

        for (Review review : reviews){
            GetReviewByProductIdResponseDto dto = new GetReviewByProductIdResponseDto(review.getUserEntity().getName(),review.getContent(),review.getRating(),review.getRegDate());
            result.add(dto);
        }
        return result;
    }

    /* 리뷰 수정 등록 버튼 */
    @Override
    @Transactional
    public void updateReview(UpdateReviewRequestDto updateReviewRequestDto){
        String account = SecurityUtil.getCurrentUsername();

        Long productId = updateReviewRequestDto.getProductId();
        String content = updateReviewRequestDto.getContent();
        double rating = updateReviewRequestDto.getRating();
        LocalDateTime regDate = LocalDateTime.now();

        reviewRepository.updateReview(productId, account, content, rating, regDate);

        // 수정된 평점을 상품 평점평균에 반영
        Book book = bookRepository.findBookByBookId(productId).get();
        book.calAverageRating();

    }

    /* 리뷰 삭제 버튼 */
    @Override
    @Transactional
    public void deleteReview(DeleteReviewRequestDto deleteReviewRequestDto){
        String account = SecurityUtil.getCurrentUsername();
        Long productId = deleteReviewRequestDto.getProductId();

        Review review = reviewRepository.findReviewByProductIdAndUserEntityAccount(productId, account).get();
        reviewRepository.deleteReview(review);

        // 평점 평균 업데이트
        Book book = bookRepository.findBookByBookId(productId).get();
        book.getReviews().remove(review);
        book.calAverageRating();
    }
}
