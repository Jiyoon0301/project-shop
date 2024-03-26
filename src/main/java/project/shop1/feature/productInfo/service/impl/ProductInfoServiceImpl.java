package project.shop1.feature.productInfo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.entity.Book;
import project.shop1.entity.CartItem;
import project.shop1.entity.UserEntity;
import project.shop1.feature.join.repository.JoinRepository;
import project.shop1.feature.productInfo.dto.AddCartRequestDto;
import project.shop1.feature.productInfo.dto.ProductInfoRequestDto;
import project.shop1.feature.productInfo.repository.ProductInfoRepository;
import project.shop1.feature.productInfo.service.ProductInfoService;
import project.shop1.feature.productSearch.dto.SearchBookByRankingRequestDto;
import project.shop1.feature.productSearch.repository.ProductSearchRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductInfoServiceImpl implements ProductInfoService {

    private final ProductInfoRepository productInfoRepository;
    private final JoinRepository joinRepository;

    /* 상품 상세 페이지 */
    @Override
    public Optional<Book> productInfo(ProductInfoRequestDto productInfoRequestDto) {
        Long productNubmer = productInfoRequestDto.getProductNumber();
        Optional<Book> result = productInfoRepository.findBookByProductNumber(productNubmer);
        if(result.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "등록된 상품이 없습니다.");
        }
        return result;
    }

    /* 장바구니 담기 버튼 */
    @Override
    public void addCart(AddCartRequestDto addCartRequestDto){
        String account = addCartRequestDto.getAccount();
        Long productNumber = addCartRequestDto.getProductNumber();
        int quantity = addCartRequestDto.getQuantity();

        Optional<UserEntity> userEntity = joinRepository.findUserEntityByAccount(account);
        if (userEntity.isEmpty()){
//            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        Optional<Book> book = productInfoRepository.findBookByProductNumber(productNumber);

        CartItem cartItem = CartItem.builder()
                .userEntity(userEntity.get())
                .book(book.get())
                .quantity(quantity)
                .build();
        productInfoRepository.addCart(cartItem);

    }

}
