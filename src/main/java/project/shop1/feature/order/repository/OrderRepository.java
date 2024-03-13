//package project.shop1.feature.order.repository;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//import project.shop1.entity.Item;
//import project.shop1.entity.Order;
//import project.shop1.feature.quantityManagement.repository.QuantityManagementRepository;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class OrderRepository {
//
//    private final EntityManager entityManager;
//
////    private final JPAQueryFactory jpaQueryFactory;
//
//    public void save(Order order){
//        entityManager.persist(order);
//    }
//    public Order findOne(Long id){
//        return entityManager.find(Order.class,id);
//    }
//
//    public Item findItemByName(String itemName) {
//        return null;
//    }
//
////    public List<Order> findAll(OrderSearch orderSearch){}
//}
