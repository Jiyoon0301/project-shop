package project.shop1.feature.admin.repository.repositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.Order;
import project.shop1.feature.admin.repository.ViewOrderRepository;

import java.util.List;

import static project.shop1.entity.QOrder.order;


@Repository
public class ViewOrderRepositoryImpl implements ViewOrderRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ViewOrderRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    /* 주문 현황 리스트 조회 */
    @Override
    public List<Order> findAllOrderList(){
        List<Order> result = jpaQueryFactory
                .selectFrom(order)
                .fetch();

        return result;
    }

    /* userEntity account로 주문 현황 리스트 조회 */
    @Override
    public List<Order> findOrderByUserEntityAccount(String account){
        List<Order> result = jpaQueryFactory
                .selectFrom(order)
                .where(order.userEntity.account.eq(account))
                .fetch();

        return result;
    }

}
