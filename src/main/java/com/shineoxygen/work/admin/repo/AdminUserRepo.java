package com.shineoxygen.work.admin.repo;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.base.model.page.Page;
import com.shineoxygen.work.base.repo.UndeletedMongoRepository;

public interface AdminUserRepo extends UndeletedMongoRepository<AdminUser, Serializable> {
	AdminUser findByUserNameAndPwd(String userName, String pwd);

	AdminUser findByUserName(String userName);
}
