package project.shop1.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import project.shop1.domain.cart.entity.CartItem;
import project.shop1.domain.order.entity.Order;
import project.shop1.domain.product.entity.Review;
import project.shop1.domain.user.enums.UserRank;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -374783848L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final StringPath account = createString("account");

    public final StringPath address = createString("address");

    public final ListPath<CartItem, project.shop1.entity.QCartItem> cartItems = this.<CartItem, project.shop1.entity.QCartItem>createList("cartItems", CartItem.class, project.shop1.entity.QCartItem.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath loginType = createString("loginType");

    public final StringPath name = createString("name");

    public final ListPath<Order, project.shop1.entity.QOrder> orders = this.<Order, project.shop1.entity.QOrder>createList("orders", Order.class, project.shop1.entity.QOrder.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<Review, project.shop1.entity.QReview> reviews = this.<Review, project.shop1.entity.QReview>createList("reviews", Review.class, project.shop1.entity.QReview.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final EnumPath<UserRank> userRank = createEnum("userRank", UserRank.class);

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

