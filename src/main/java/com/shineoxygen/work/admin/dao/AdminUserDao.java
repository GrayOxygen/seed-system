package com.shineoxygen.work.admin.dao;

import java.io.Serializable;

import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.base.dao.BaseMongoRepository;
public interface AdminUserDao extends BaseMongoRepository<AdminUser, Serializable> {

}
