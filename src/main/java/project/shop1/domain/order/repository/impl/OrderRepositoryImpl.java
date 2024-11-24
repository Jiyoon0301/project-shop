package project.shop1.domain.order.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.domain.order.entity.Order;
import project.shop1.domain.order.repository.OrderRepository;
import static project.shop1.entity.QUserEntity.userEntity;
import static project.shop1.entity.QOrder.order;

import java.util.List;


@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public OrderRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }
//    public Order findOne(Long id){
//        return entityManager.find(Order.class,id);
//    }
//
//    public Book findItemByName(String itemName) {
//        return null;
//    }

    /* 주문 저장 */
    @Override
    public void saveOrder(Order order){
        entityManager.persist(order);
    }

    /* 주소 저장 */
    @Override
    public void saveAddress(String account, String roadAddress, String detailedAddress) {
        jpaQueryFactory
                .update(userEntity)
                .where(userEntity.account.eq(account))
                .set(userEntity.address, roadAddress + " " + detailedAddress)
                .execute();
    }

    /* userEntity account로 order 찾기 */
    @Override
    public List<Order> findOrdersByUserEntityAccount(String account){
        List<Order> findOrder = jpaQueryFactory
                .selectFrom(order)
                .where(order.userEntity.account.eq(account))
                .orderBy(order.orderDate.desc())
                .fetch();
        return findOrder;
    }



//    public List<Order> findAll(OrderSearch orderSearch){}
}
