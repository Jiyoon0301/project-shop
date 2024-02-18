package project.shop1.feature.search.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;
import project.shop1.entity.Item;
import project.shop1.entity.QItem;


import java.util.List;
import static project.shop1.entity.QItem.item;

@Repository
@RequiredArgsConstructor
public class SearchRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    public List<String> searchRanking(){
        List<String> itemNames = jpaQueryFactory
                .select(QItem.item.name)
                .from(QItem.item)
                .orderBy(QItem.item.sold.desc())
                .fetch();
        return itemNames;
    }


}
