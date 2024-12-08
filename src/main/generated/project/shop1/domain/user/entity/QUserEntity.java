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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final StringPath account = createString("account");

    public final project.shop1.domain.address.entity.QAddress address;

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
        this(UserEntity.class, forVariable(variable), INITS);
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserEntity(PathMetadata metadata, PathInits inits) {
        this(UserEntity.class, metadata, inits);
    }

    public QUserEntity(Class<? extends UserEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new project.shop1.domain.address.entity.QAddress(forProperty("address")) : null;
    }

}

