package project.shop1.domain.review.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.repository.ProductRepository;
import project.shop1.domain.review.dto.GetReviewsRequestDto;
import project.shop1.domain.review.dto.GetReviewsResponseDto;
import project.shop1.domain.review.dto.ReviewRequestDto;
import project.shop1.domain.review.entity.Review;
import project.shop1.domain.review.repository.ReviewRepository;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.global.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class) //Junit5
public class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    void 리뷰_등록_성공() {
        // given
        Long productId = 1L;
        Long userId = 2L;
        ReviewRequestDto requestDto = new ReviewRequestDto(productId, userId, "Good", 5);

        Book mockBook = mock(Book.class);
        UserEntity mockUser = mock(UserEntity.class);

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockBook));
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // when
        reviewService.createReview(requestDto);

        // then
        verify(productRepository, times(1)).findById(productId);
        verify(userRepository, times(1)).findById(userId);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void 유효하지_않은_상품_아이디로_리뷰_등록을_시도하면_예외가_발생한다() {
        // given
        Long invalidProductId = 999L;
        Long userId = 2L;
        ReviewRequestDto requestDto = new ReviewRequestDto(invalidProductId, userId, "Great book!", 5);

        when(productRepository.findById(invalidProductId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> reviewService.createReview(requestDto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("유효하지 않은 상품 ID입니다.");

        verify(productRepository, times(1)).findById(invalidProductId);
        verify(userRepository, never()).findById(any());
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void 특정_상품에_대한_리뷰_가져오기_성공() {
        // given
        Long productId = 1L;
        GetReviewsRequestDto requestDto = new GetReviewsRequestDto(0, 5, "rating");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rating"));

        Review review1 = Review.builder()
                .id(1L)
                .content("Good")
                .rating(5)
                .userEntity(UserEntity.builder().name("name1").build())
                .regDate(LocalDateTime.now())
                .build();

        Review review2 = Review.builder()
                .id(2L)
                .content("bad")
                .rating(4)
                .userEntity(UserEntity.builder().name("name2").build())
                .regDate(LocalDateTime.now())
                .build();

        List<Review> reviewList = Arrays.asList(review1, review2);
        Page<Review> reviewPage = new PageImpl<>(reviewList, pageable, reviewList.size());

        when(reviewRepository.findByProductIdAndRating(productId, 5, pageable)).thenReturn(reviewPage);
        // when
        Page<GetReviewsResponseDto> responseDtos = reviewService.getReviewsByProduct(productId, requestDto);

        // then
        assertThat(responseDtos).isNotNull();
        assertThat(responseDtos.getContent()).hasSize(2); // 리뷰 개수 검증

        GetReviewsResponseDto dto1 = responseDtos.getContent().get(0);
        assertThat(dto1.getContent()).isEqualTo("Good");
        assertThat(dto1.getRating()).isEqualTo(5);
        assertThat(dto1.getUserName()).isEqualTo("name1");

        GetReviewsResponseDto dto2 = responseDtos.getContent().get(1);
        assertThat(dto2.getContent()).isEqualTo("bad");
        assertThat(dto2.getRating()).isEqualTo(4);
        assertThat(dto2.getUserName()).isEqualTo("name2");

        verify(reviewRepository, times(1)).findByProductIdAndRating(productId, 5, pageable);
    }
}
