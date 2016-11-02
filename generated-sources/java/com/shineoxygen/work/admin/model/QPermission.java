package com.shineoxygen.work.admin.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPermission is a Querydsl query type for Permission
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPermission extends EntityPathBase<Permission> {

    private static final long serialVersionUID = -610159104L;

    public static final QPermission permission = new QPermission("permission");

    public final com.shineoxygen.work.base.model.QUnDeletedEntity _super = new com.shineoxygen.work.base.model.QUnDeletedEntity(this);

    public final BooleanPath buildin = createBoolean("buildin");

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final StringPath id = _super.id;

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Integer> showOrder = createNumber("showOrder", Integer.class);

    public final StringPath url = createString("url");

    public QPermission(String variable) {
        super(Permission.class, forVariable(variable));
    }

    public QPermission(Path<? extends Permission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPermission(PathMetadata metadata) {
        super(Permission.class, metadata);
    }

}

