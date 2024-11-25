package project.shop1.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuthor is a Querydsl query type for Author
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthor extends EntityPathBase<Author> {

    private static final long serialVersionUID = -468147787L;

    public static final QAuthor author = new QAuthor("author");

    public final StringPath authorIntro = createString("authorIntro");

    public final ListPath<Book, QBook> book = this.<Book, QBook>createList("book", Book.class, QBook.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath nation = createString("nation");

    public final DatePath<java.time.LocalDate> regDate = createDate("regDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> updateDate = createDate("updateDate", java.time.LocalDate.class);

    public QAuthor(String variable) {
        super(Author.class, forVariable(variable));
    }

    public QAuthor(Path<? extends Author> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthor(PathMetadata metadata) {
        super(Author.class, metadata);
    }

}

