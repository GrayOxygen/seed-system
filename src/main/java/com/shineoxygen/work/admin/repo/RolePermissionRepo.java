package com.shineoxygen.work.admin.repo;

import java.io.Serializable;

import com.shineoxygen.work.admin.model.RolePermission;
import com.shineoxygen.work.base.repo.UndeletedMongoRepository;

public interface RolePermissionRepo extends UndeletedMongoRepository<RolePermission, Serializable> {
}
