package com.shineoxygen.work.base.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseEntity is a Querydsl query type for BaseEntity
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QBaseEntity extends BeanPath<BaseEntity> {

    private static final long serialVersionUID = 2086117001L;

    public static final QBaseEntity baseEntity = new QBaseEntity("baseEntity");

    public final StringPath id = createString("id");

    public final BooleanPath new$ = createBoolean("new");

    public QBaseEntity(String variable) {
        super(BaseEntity.class, forVariable(variable));
    }

    public QBaseEntity(Path<? extends BaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseEntity(PathMetadata metadata) {
        super(BaseEntity.class, metadata);
    }

}

