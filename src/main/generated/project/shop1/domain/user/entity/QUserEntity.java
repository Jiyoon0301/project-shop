package project.shop1.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -374783848L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final StringPath account = createString("account");

    public final StringPath address = createString("address");

    public final ListPath<project.shop1.domain.cart.entity.CartItem, project.shop1.domain.cart.entity.QCartItem> cartItems = this.<project.shop1.domain.cart.entity.CartItem, project.shop1.domain.cart.entity.QCartItem>createList("cartItems", project.shop1.domain.cart.entity.CartItem.class, project.shop1.domain.cart.entity.QCartItem.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath loginType = createString("loginType");

    public final StringPath name = createString("name");

    public final ListPath<project.shop1.domain.order.entity.Order, project.shop1.domain.order.entity.QOrder> orders = this.<project.shop1.domain.order.entity.Order, project.shop1.domain.order.entity.QOrder>createList("orders", project.shop1.domain.order.entity.Order.class, project.shop1.domain.order.entity.QOrder.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<project.shop1.domain.review.entity.Review, project.shop1.domain.review.entity.QReview> reviews = this.<project.shop1.domain.review.entity.Review, project.shop1.domain.review.entity.QReview>createList("reviews", project.shop1.domain.review.entity.Review.class, project.shop1.domain.review.entity.QReview.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final EnumPath<project.shop1.domain.user.enums.UserRank> userRank = createEnum("userRank", project.shop1.domain.user.enums.UserRank.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

