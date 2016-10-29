package com.shineoxygen.work.base.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QUser extends BeanPath<User> {

    private static final long serialVersionUID = -643526656L;

    public static final QUser user = new QUser("user");

    public final QUnDeletedEntity _super = new QUnDeletedEntity(this);

    public final DateTimePath<java.util.Date> atime = createDateTime("atime", java.util.Date.class);

    public final DateTimePath<java.util.Date> ctime = createDateTime("ctime", java.util.Date.class);

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final StringPath id = _super.id;

    public final DateTimePath<java.util.Date> mtime = createDateTime("mtime", java.util.Date.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath pwd = createString("pwd");

    public final StringPath userName = createString("userName");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

