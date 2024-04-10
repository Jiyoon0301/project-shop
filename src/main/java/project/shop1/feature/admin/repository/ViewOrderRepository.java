package project.shop1.feature.admin.repository;

import project.shop1.entity.Order;
import project.shop1.feature.admin.dto.viewOrderDto.ViewOrderResponseDto;

import java.util.List;

public interface ViewOrderRepository {

    List<Order> findAllOrderList();

    /* userEntity account로 주문 리스트 조회 */
    List<Order> findOrderByUserEntityAccount(String account);


    }
