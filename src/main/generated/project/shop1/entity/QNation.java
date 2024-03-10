package project.shop1.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNation is a Querydsl query type for Nation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNation extends EntityPathBase<Nation> {

    private static final long serialVersionUID = 1188977866L;

    public static final QNation nation = new QNation("nation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QNation(String variable) {
        super(Nation.class, forVariable(variable));
    }

    public QNation(Path<? extends Nation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNation(PathMetadata metadata) {
        super(Nation.class, metadata);
    }

}

