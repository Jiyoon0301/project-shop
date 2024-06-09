package project.shop1.feature.myPage.getAllReviews.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.repository.UserRepository;
import project.shop1.common.security.SecurityUtil;
import project.shop1.entity.Review;
import project.shop1.entity.UserEntity;
import project.shop1.feature.myPage.getAllReviews.dto.GetAllReviewsResponseDto;
import project.shop1.feature.myPage.getAllReviews.repository.GetAllReviewsRepository;
import project.shop1.feature.myPage.getAllReviews.service.GetAllReviewsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllReviewsServiceImpl implements GetAllReviewsService {

    private final GetAllReviewsRepository getAllReviewsRepository;
    private final UserRepository userRepository;

    // account 로 회원 찾고 review 찾기
    @Override
    public List<GetAllReviewsResponseDto> getAllReviews(){
        String account = SecurityUtil.getCurrentUsername();
        UserEntity user = userRepository.findUserEntityByAccount(account).get();
        List<Review> reviews = user.getReviews();

        List<GetAllReviewsResponseDto> result = new ArrayList<>();
        for(Review review : reviews){
            String bookTitle = review.getBook().getTitle();
            String content = review.getContent();
            double rating = review.getRating();
            LocalDateTime regDate = review.getRegDate();

            GetAllReviewsResponseDto getAllReviewsResponseDto = GetAllReviewsResponseDto.builder()
                    .bookTitle(bookTitle)
                    .content(content)
                    .rating(rating)
                    .regDate(regDate)
                    .build();
            result.add(getAllReviewsResponseDto);
        }

        return result;
    }
}
