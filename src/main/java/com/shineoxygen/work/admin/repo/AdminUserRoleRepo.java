package com.shineoxygen.work.admin.repo;

import java.io.Serializable;

import com.shineoxygen.work.admin.model.AdminUserRole;
import com.shineoxygen.work.base.repo.UndeletedMongoRepository;

public interface AdminUserRoleRepo extends UndeletedMongoRepository<AdminUserRole, Serializable> {

}
