package com.shineoxygen.work.admin.repo;

import java.io.Serializable;

import com.shineoxygen.work.admin.model.Role;
import com.shineoxygen.work.base.repo.UndeletedMongoRepository;

public interface RoleRepo extends UndeletedMongoRepository<Role, Serializable> {
}
