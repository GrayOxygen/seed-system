package com.shineoxygen.work.admin.repo;

import java.io.Serializable;

import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.base.repo.UndeletedMongoRepository;

public interface AdminUserRepo extends UndeletedMongoRepository<AdminUser, Serializable> {
	AdminUser findByUserNameAndPwd(String userName, String pwd);

	AdminUser findByUserName(String userName);
}
