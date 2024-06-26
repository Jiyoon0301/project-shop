package project.shop1.feature.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.entity.Book;
import project.shop1.feature.main.dto.GetProductsByRankingRequestDto;
import project.shop1.feature.main.dto.GetProductsByRankingResponseDto;
import project.shop1.feature.main.repository.MainRepository;
import project.shop1.feature.main.service.MainService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainServiceImpl implements MainService {

    private final MainRepository mainRepository;

    /* 판매 순 상품 노출 */
    @Override
    public List<GetProductsByRankingResponseDto> getProductsByRanking(GetProductsByRankingRequestDto getProductsByRankingRequestDto){
        int page = getProductsByRankingRequestDto.getPage();
        List<Book> bookList = mainRepository.findProductsByRanking(page);
        List<GetProductsByRankingResponseDto> result = new ArrayList<>();

        for (Book book : bookList){
            GetProductsByRankingResponseDto dto = new GetProductsByRankingResponseDto(book.getTitle(), book.getCategory(), book.getAverageRating());
            result.add(dto);
        }
        return result;
    }
}
