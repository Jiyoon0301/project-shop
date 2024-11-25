package project.shop1.domain.product.productSearch_needRefactor.service;

import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.productSearch_needRefactor.dto.SearchBookByRankingRequestDto;
import project.shop1.domain.product.productSearch_needRefactor.dto.findByKeywordAndCategoryRequestDto;

import java.util.List;

public interface ProductSearchService {

    /* 상품 검색 - 검색어, 카테고리 */
    List<Book> findByKeywordAndCategory(findByKeywordAndCategoryRequestDto findByKeywordAndCategoryRequestDto);

    /* 상품 인기순위(판매순)으로 조회*/
    List<Book> searchBookByRanking(SearchBookByRankingRequestDto searchBookByRankingRequestDto);


    }
