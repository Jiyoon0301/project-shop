//package project.shop1.feature.quantityManagement.repository;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import project.shop1.entity.Item;
//
//import java.util.List;
//
//import static project.shop1.entity.QItem.item;
//
//@Repository
//@RequiredArgsConstructor
//public class QuantityManagementRepository {
//
//    @PersistenceContext
//    private final EntityManager entityManager;
//
//    private final JPAQueryFactory jpaQueryFactory;
//
//    public void save(Item item){
//        if (item.getId()==null){
//            entityManager.persist(item);
//        }else{
//            entityManager.merge(item); //이미 있는 item을 업데이트 느낌
//        }
//    }
//
//    public Item findOne(Long id){
//        return entityManager.find(Item.class, id);
//    }
//
//    public List<Item> findAll(){
//        return jpaQueryFactory
//                .selectFrom(item)
//                .fetch();
//    }
//
//    public Item findItemByName(String itemName) {
//        Item result = jpaQueryFactory
//                .selectFrom(item)
//                .where(item.name.eq(itemName))
//                .fetchOne();
//        return result;
//    }
//
//
//
//}
