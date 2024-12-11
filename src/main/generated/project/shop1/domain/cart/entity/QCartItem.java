package project.shop1.domain.cart.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCartItem is a Querydsl query type for CartItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCartItem extends EntityPathBase<CartItem> {

    private static final long serialVersionUID = 1577886514L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCartItem cartItem = new QCartItem("cartItem");

    public final project.shop1.domain.product.entity.QBook book;

    public final QCart cart;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final project.shop1.domain.user.entity.QUserEntity userEntity;

    public QCartItem(String variable) {
        this(CartItem.class, forVariable(variable), INITS);
    }

    public QCartItem(Path<? extends CartItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCartItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCartItem(PathMetadata metadata, PathInits inits) {
        this(CartItem.class, metadata, inits);
    }

    public QCartItem(Class<? extends CartItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new project.shop1.domain.product.entity.QBook(forProperty("book")) : null;
        this.cart = inits.isInitialized("cart") ? new QCart(forProperty("cart"), inits.get("cart")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new project.shop1.domain.user.entity.QUserEntity(forProperty("userEntity"), inits.get("userEntity")) : null;
    }

}
