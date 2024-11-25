package project.shop1.domain.product.productSearch_needRefactor.repository;

import project.shop1.domain.product.entity.Book;

import java.util.List;
import java.util.Optional;

public interface ProductSearchRepository {

    /* 상품 검색 - 검색어, 카테고리 */
    Optional<List<Book>> findByKeywordAndCategory(String criteria, String keyword, String category, int amount, int pageNum);

    /* 인기순위(판매순)으로 상품 조회 */
    Optional<List<Book>> findBookByRanking(int amount, int pageNum);

    }
