package com.shineoxygen.work.admin.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRole is a Querydsl query type for Role
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRole extends EntityPathBase<Role> {

    private static final long serialVersionUID = 1102162279L;

    public static final QRole role = new QRole("role");

    public final com.shineoxygen.work.base.model.QUnDeletedEntity _super = new com.shineoxygen.work.base.model.QUnDeletedEntity(this);

    public final BooleanPath buildin = createBoolean("buildin");

    public final DateTimePath<java.util.Date> ctime = createDateTime("ctime", java.util.Date.class);

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final StringPath id = _super.id;

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public QRole(String variable) {
        super(Role.class, forVariable(variable));
    }

    public QRole(Path<? extends Role> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRole(PathMetadata metadata) {
        super(Role.class, metadata);
    }

}

