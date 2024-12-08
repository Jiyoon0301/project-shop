package project.shop1.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = -1032864365L;

    public static final QBook book = new QBook("book");

    public final StringPath authorName = createString("authorName");

    public final StringPath category = createString("category");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<project.shop1.domain.review.entity.Review, project.shop1.domain.review.entity.QReview> reviews = this.<project.shop1.domain.review.entity.Review, project.shop1.domain.review.entity.QReview>createList("reviews", project.shop1.domain.review.entity.Review.class, project.shop1.domain.review.entity.QReview.class, PathInits.DIRECT2);

    public final NumberPath<Integer> stockQuantity = createNumber("stockQuantity", Integer.class);

    public final StringPath title = createString("title");

    public QBook(String variable) {
        super(Book.class, forVariable(variable));
    }

    public QBook(Path<? extends Book> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBook(PathMetadata metadata) {
        super(Book.class, metadata);
    }

}

