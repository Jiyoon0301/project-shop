//package project.shop1.feature.order.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import project.shop1.entity.enums.OrderStatus;
//import project.shop1.common.repository.impl.UserRepositoryImpl;
//import project.shop1.entity.*;
//import project.shop1.feature.order.repository.OrderRepository;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class OrderServicee {
//
//    private final OrderRepository orderRepository;
//    private final UserRepositoryImpl userRepositoryImpl;
//
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
//
//    /**
//     * 주문
//     */
//    @Transactional
//    public void order(Long userId, Long itemId, int count){
//        //엔티티 조회
//        UserEntity userEntity = userRepositoryImpl.findOne(userId);
//    }
//
//
//
//}
