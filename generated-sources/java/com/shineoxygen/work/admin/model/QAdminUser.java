package com.shineoxygen.work.admin.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdminUser is a Querydsl query type for AdminUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdminUser extends EntityPathBase<AdminUser> {

    private static final long serialVersionUID = 711649641L;

    public static final QAdminUser adminUser = new QAdminUser("adminUser");

    public final com.shineoxygen.work.base.model.QUser _super = new com.shineoxygen.work.base.model.QUser(this);

    //inherited
    public final DateTimePath<java.util.Date> atime = _super.atime;

    public final BooleanPath buildin = createBoolean("buildin");

    //inherited
    public final DateTimePath<java.util.Date> ctime = _super.ctime;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath email = createString("email");

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final DateTimePath<java.util.Date> mtime = _super.mtime;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath phoneNum = createString("phoneNum");

    //inherited
    public final StringPath pwd = _super.pwd;

    public final StringPath userName = createString("userName");

    public final EnumPath<com.shineoxygen.work.base.model.enums.UserStatus> userStatus = createEnum("userStatus", com.shineoxygen.work.base.model.enums.UserStatus.class);

    public QAdminUser(String variable) {
        super(AdminUser.class, forVariable(variable));
    }

    public QAdminUser(Path<? extends AdminUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminUser(PathMetadata metadata) {
        super(AdminUser.class, metadata);
    }

}

