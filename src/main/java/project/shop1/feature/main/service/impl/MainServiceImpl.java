package project.shop1.feature.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.Book;
import project.shop1.feature.main.dto.GetProductsByRatingRequestDto;
import project.shop1.feature.main.dto.GetProductsByRatingResponseDto;
import project.shop1.feature.main.repository.MainRepository;
import project.shop1.feature.main.service.MainService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainServiceImpl implements MainService {

    private final MainRepository mainRepository;

    /* 평점 순 상품 노출 */
    @Override
    public List<GetProductsByRatingResponseDto> getProductsByRating(GetProductsByRatingRequestDto getProductsByRatingRequestDto){
        int page = getProductsByRatingRequestDto.getPage();
        List<Book> bookList = mainRepository.findProductsByRating(page);
        List<GetProductsByRatingResponseDto> result = new ArrayList<>();

        for (Book book : bookList){
            GetProductsByRatingResponseDto dto = new GetProductsByRatingResponseDto(book.getTitle(), book.getCategory(), book.getAverageRating());
            result.add(dto);
        }
        return result;
    }
}
