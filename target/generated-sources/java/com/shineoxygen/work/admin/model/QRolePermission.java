package com.shineoxygen.work.admin.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRolePermission is a Querydsl query type for RolePermission
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRolePermission extends EntityPathBase<RolePermission> {

    private static final long serialVersionUID = -764545386L;

    public static final QRolePermission rolePermission = new QRolePermission("rolePermission");

    public final com.shineoxygen.work.base.model.QUnDeletedEntity _super = new com.shineoxygen.work.base.model.QUnDeletedEntity(this);

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath permissionId = createString("permissionId");

    public final StringPath roleId = createString("roleId");

    public QRolePermission(String variable) {
        super(RolePermission.class, forVariable(variable));
    }

    public QRolePermission(Path<? extends RolePermission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRolePermission(PathMetadata metadata) {
        super(RolePermission.class, metadata);
    }

}

