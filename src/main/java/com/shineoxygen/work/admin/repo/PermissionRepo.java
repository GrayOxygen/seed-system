package com.shineoxygen.work.admin.repo;

import java.io.Serializable;

import com.shineoxygen.work.admin.model.Permission;
import com.shineoxygen.work.base.repo.UndeletedMongoRepository;

public interface PermissionRepo extends UndeletedMongoRepository<Permission, Serializable> {
}
