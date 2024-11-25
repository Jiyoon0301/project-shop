package project.shop1.domain.order.cancelOrder_needRefactor.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.domain.order.enums.OrderStatus;
import project.shop1.domain.order.cancelOrder_needRefactor.repository.CancelOrderRepository;


import static project.shop1.entity.QOrder.order;

@Repository
public class CancelOrderRepositoryImpl implements CancelOrderRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public CancelOrderRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    /* 주문 테이블의 orderStatus -> cancel */
    @Override
    public void orderCancel(Long orderId){
        Long count = jpaQueryFactory
                .update(order)
                .where(order.id.eq(orderId))
                .set(order.orderStatus, OrderStatus.CANCEL)
                .execute();
    }
}
