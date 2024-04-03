package project.shop1.feature.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.common.repository.impl.UserRepositoryImpl;
import project.shop1.entity.*;
import project.shop1.feature.cart.repository.CartRepository;
import project.shop1.feature.join.repository.JoinRepository;
import project.shop1.feature.order.common.AddressPairs;
import project.shop1.feature.order.common.ProductInfoPairs;
import project.shop1.feature.order.dto.OrderPageRequestDto;
import project.shop1.feature.order.dto.OrderPageResponseDto;
import project.shop1.feature.order.dto.SaveAddressRequestDto;
import project.shop1.feature.order.dto.SearchAddressRequestDto;
import project.shop1.feature.order.repository.OrderRepository;
import project.shop1.feature.order.service.OrderService;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepositoryImpl userRepositoryImpl;
    private final JoinRepository joinRepository;
    private final CartRepository cartRepository;

//    @Transactional
//    public void addStock(String itemName, int quantity) {
//        Item item = orderRepository.findItemByName(itemName);
//        item.setStockQuantity(item.getStockQuantity() + quantity);
//    }
//
//    public void cancel(Order order) {
//        if (order.getDelivery().getStatus() == DeliveryStatus.COMPLETE) {
//            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
//        }
//        order.setStatus(OrderStatus.CANCEL);
//        for (OrderItem orderItem : order.getOrderItems()) {
//            addStock(orderItem.getItem().getTitle(), orderItem.getCount()); //재고 수량 원상 복구
//        }
//    }

    /* 주문 페이지 */
    @Override
    public OrderPageResponseDto orderPage(OrderPageRequestDto orderPageRequestDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        String account = (String) session.getAttribute("account"); // 세션에 저장된 사용자 정보
        UserEntity userEntity = joinRepository.findUserEntityByAccount(account).get();

        List<ProductInfoPairs> productInfoPairs = new ArrayList<>();
        int totalPrice = 0;

        /* 장바구니 구매인지 확인 */
        if (orderPageRequestDto.getIsFromCartPage()){
            List<CartItem> allCartItems = cartRepository.findAllCartItemsByUser(userEntity);
            for (CartItem cartItem : allCartItems){
                totalPrice += cartItem.getQuantity() * cartItem.getBook().getPrice();
                productInfoPairs.add(new ProductInfoPairs(cartItem.getBook().getTitle(),cartItem.getQuantity(),cartItem.getBook().getPrice()));
            }
        } else {
            Long bookId = orderPageRequestDto.getBookId();
            int count = orderPageRequestDto.getCount();
            Book bookToBuy = orderRepository.findBookbyBookId(bookId).get();
            totalPrice = count * bookToBuy.getPrice();
            productInfoPairs.add(new ProductInfoPairs(bookToBuy.getTitle(), count, bookToBuy.getPrice()));
        }

        OrderPageResponseDto orderPageResponseDto = OrderPageResponseDto.builder()
                .userEntityname(userEntity.getName())
                .userEntityPhoneNumber(userEntity.getPhoneNumber())
                .userEntityAddress(userEntity.getAddress())
                .productInfoPairs(productInfoPairs)
                .totalPrice(totalPrice)
                .deliveryFee(2500)
                .amountToPay(totalPrice+2500)
                .build();

        return orderPageResponseDto;

    }
    /* 주문 */
//    @Transactional
//    public void order(Long userId, Long itemId, int count){
//        //엔티티 조회
//        UserEntity userEntity = userRepositoryImpl.findOne(userId);
//    }

    /* 주소 검색 */
    @Override
    public List<AddressPairs> searchAddress(SearchAddressRequestDto searchAddressRequestDto) {
        String keyword = searchAddressRequestDto.getKeyword();
        int pageNumber = searchAddressRequestDto.getPageNumber();

        URI uri = UriComponentsBuilder
                .fromUriString("https://business.juso.go.kr")
                .path("/addrlink/addrLinkApi.do")
                .queryParam("keyword", keyword)
                .queryParam("confmKey", "devU01TX0FVVEgyMDI0MDIyNjIwMTE1OTExNDU0NzY=")
                .queryParam("currentPage", pageNumber)
                .queryParam("countPerPage", 10)
                .queryParam("resultType", "json")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();

        log.info("uri : {}", uri);

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .build();

        ResponseEntity<String> fullAddress = restTemplate.exchange(req, String.class);

        try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(fullAddress.getBody());
        JsonNode jusoNode = rootNode.path("results").path("juso");
        List<AddressPairs> addressPairs = new ArrayList<>();

        for (JsonNode node : jusoNode) {
            String roadAddrPart1 = node.path("roadAddrPart1").asText();
            String jibunAddr = node.path("jibunAddr").asText();

            addressPairs.add(new AddressPairs(roadAddrPart1, jibunAddr));
        }
            return addressPairs;
        }catch (JsonProcessingException e){
            e.printStackTrace();
            throw new BusinessException(ErrorCode.RESOURCE_ACCESS_NOT_ACCEPTABLE,"fail");
        }
    }

    /* 주소 저장 */
    @Override
    @Transactional
    public void saveAddress(SaveAddressRequestDto saveAddressRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String account = (String) session.getAttribute("account"); // 세션에 저장된 사용자 정보

        String roadAddress = saveAddressRequestDto.getRoadAddress();
        String detailedAddress = saveAddressRequestDto.getDetailedAddress();

        orderRepository.saveAddress(account, roadAddress, detailedAddress);
    }


}
