package com.shineoxygen.work.admin.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdminUserRole is a Querydsl query type for AdminUserRole
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdminUserRole extends EntityPathBase<AdminUserRole> {

    private static final long serialVersionUID = -1094909569L;

    public static final QAdminUserRole adminUserRole = new QAdminUserRole("adminUserRole");

    public final com.shineoxygen.work.base.model.QUnDeletedEntity _super = new com.shineoxygen.work.base.model.QUnDeletedEntity(this);

    public final StringPath adminUserId = createString("adminUserId");

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath roleId = createString("roleId");

    public QAdminUserRole(String variable) {
        super(AdminUserRole.class, forVariable(variable));
    }

    public QAdminUserRole(Path<? extends AdminUserRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminUserRole(PathMetadata metadata) {
        super(AdminUserRole.class, metadata);
    }

}

