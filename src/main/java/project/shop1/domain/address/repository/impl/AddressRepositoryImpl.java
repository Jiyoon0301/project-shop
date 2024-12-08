package project.shop1.domain.address.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import project.shop1.domain.address.repository.AddressRepositoryCustom;

import static project.shop1.domain.user.entity.QUserEntity.userEntity;
//import static project.shop1.domain.address.entity.QAddress.address;

@Repository
public class AddressRepositoryImpl implements AddressRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public AddressRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public void save(String account, String roadAddress, String detailedAddress) {

    }
}
