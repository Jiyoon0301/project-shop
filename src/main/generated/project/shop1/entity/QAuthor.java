package project.shop1.entity;

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

    private static final long serialVersionUID = 835268366L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuthor author = new QAuthor("author");

    public final StringPath authorIntro = createString("authorIntro");

    public final StringPath authorName = createString("authorName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QNation nation;

    public QAuthor(String variable) {
        this(Author.class, forVariable(variable), INITS);
    }

    public QAuthor(Path<? extends Author> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuthor(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuthor(PathMetadata metadata, PathInits inits) {
        this(Author.class, metadata, inits);
    }

    public QAuthor(Class<? extends Author> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nation = inits.isInitialized("nation") ? new QNation(forProperty("nation")) : null;
    }

}

