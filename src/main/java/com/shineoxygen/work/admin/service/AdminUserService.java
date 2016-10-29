package com.shineoxygen.work.admin.service;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.admin.model.QAdminUser;
import com.shineoxygen.work.admin.repo.AdminUserRepo;
import com.shineoxygen.work.base.model.bootstraptable.SentParameters;
import com.shineoxygen.work.base.model.bootstraptable.TablePage;
import com.shineoxygen.work.base.model.page.QueryCondition;

@Service
public class AdminUserService {
	private static final Logger logger = LogManager.getLogger(AdminUserService.class);
	@Autowired
	private AdminUserRepo adminUserDao;

	/**
	 * 获取所有用户
	 *
	 * @return
	 */
	public List<AdminUser> findAll() {
		return adminUserDao.findAll();
	}

	public void save(AdminUser user) {
		adminUserDao.save(user);
	}

	public AdminUser findByUserNameAndPwd(String userName, String pwd) {
		QAdminUser user = new QAdminUser("adminUser");
		return adminUserDao.findOne(user.userName.eq(userName).and(user.pwd.eq(pwd)).and(user.deleted.ne(true)));
	}

	public TablePage<AdminUser> bdTableList(SentParameters sentParameters) {
		QAdminUser user = new QAdminUser("adminUser");
		BooleanExpression expression = user.deleted.ne(true);
		if (sentParameters.containsNotNull("userName")) {
			expression = expression.and(user.userName.like("%" + sentParameters.getValue("userName") + "%"));
		}
		return adminUserDao.bdTableList(expression, sentParameters.toSpringPage());
	}

	public TablePage<AdminUser> bdTableList(Pageable pageable, QueryCondition queryCondition) {
		QAdminUser user = new QAdminUser("adminUser");
		BooleanExpression expression = user.deleted.ne(true);
		if (queryCondition.containsNotEmpty("userName")) {
			expression = expression.and(user.userName.like("%" + queryCondition.getValue("userName") + "%"));
		}
		return adminUserDao.bdTableList(expression, pageable);
	}

	public void softDelete(String... ids) {
		adminUserDao.softDelete(ids);
	}

	public boolean exist(AdminUser exist, AdminUser beside) {
		return adminUserDao.existBeside(exist, beside);
	}

	public AdminUser findByUserName(String userName) {
		QAdminUser user = new QAdminUser("adminUser");
		return adminUserDao.findOne(user.userName.eq(userName).and(user.deleted.ne(true)));
	}

	public AdminUser findById(String id) {
		QAdminUser user = new QAdminUser("adminUser");
		return adminUserDao.findOne(user.userName.eq(id).and(user.deleted.ne(true)));
	}

}