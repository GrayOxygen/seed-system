package com.shineoxygen.work.base.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUnDeletedEntity is a Querydsl query type for UnDeletedEntity
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QUnDeletedEntity extends BeanPath<UnDeletedEntity> {

    private static final long serialVersionUID = 1524843502L;

    public static final QUnDeletedEntity unDeletedEntity = new QUnDeletedEntity("unDeletedEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final BooleanPath deleted = createBoolean("deleted");

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public QUnDeletedEntity(String variable) {
        super(UnDeletedEntity.class, forVariable(variable));
    }

    public QUnDeletedEntity(Path<? extends UnDeletedEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUnDeletedEntity(PathMetadata metadata) {
        super(UnDeletedEntity.class, metadata);
    }

}

