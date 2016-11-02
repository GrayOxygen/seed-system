package com.shineoxygen.work.admin.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.admin.model.AdminUserRole;
import com.shineoxygen.work.admin.model.QAdminUser;
import com.shineoxygen.work.admin.model.QAdminUserRole;
import com.shineoxygen.work.admin.repo.AdminUserRepo;
import com.shineoxygen.work.admin.repo.AdminUserRoleRepo;
import com.shineoxygen.work.base.model.bootstraptable.SentParameters;
import com.shineoxygen.work.base.model.bootstraptable.TablePage;
import com.shineoxygen.work.base.model.page.QueryCondition;

@Service
@SuppressWarnings("unchecked")
public class AdminUserService {
	private static final Logger logger = LogManager.getLogger(AdminUserService.class);
	@Autowired
	private AdminUserRepo adminUserDao;
	@Autowired
	private AdminUserRoleRepo adminUserRoleRepo;

	/**
	 * 获取所有用户
	 *
	 * @return
	 */
	public List<AdminUser> findAll() {
		return adminUserDao.findAll();
	}

	public void save(AdminUser user, String[] rolesID) {
		adminUserDao.save(user);
		// 保存关联
		for (String roleId : rolesID) {
			AdminUserRole adminUserRole = new AdminUserRole();
			adminUserRole.setRoleId(roleId);
			adminUserRole.setAdminUserId(user.getId());
			adminUserRoleRepo.save(adminUserRole);
		}
	}

	public void update(AdminUser user, String[] rolesID) {
		adminUserDao.save(user);
		// 删除关联
		softDeleteAdminUserRole(user.getId());
		// 保存关联
		for (String roleId : rolesID) {
			AdminUserRole adminUserRole = new AdminUserRole();
			adminUserRole.setRoleId(roleId);
			adminUserRole.setAdminUserId(user.getId());
			adminUserRoleRepo.save(adminUserRole);
		}
	}

	/**
	 * 假删除用户与角色的关联
	 * 
	 * @param ids
	 *            用户id数组
	 */
	private void softDeleteAdminUserRole(String... ids) {
		QAdminUserRole qAdminUserRole = new QAdminUserRole("adminUser");

		List<String> adminUserRoleIds = (List<String>) CollectionUtils
				.collect((List<AdminUserRole>) adminUserRoleRepo.findAll(qAdminUserRole.adminUserId.in(ids).and(qAdminUserRole.deleted.ne(true))), new Transformer() {
					@Override
					public Object transform(Object input) {
						return ((AdminUserRole) input).getId();
					}
				});
		adminUserRoleRepo.softDelete(adminUserRoleIds);
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
		// 假删除关联
		softDeleteAdminUserRole(ids);

	}

	/**
	 * 排除beside条件，满足exist条件的记录是否存在，不为empty（"" null不行）的属性作为条件
	 * 
	 * @param exist
	 *            判断存在的条件
	 * @param beside
	 *            排除条件
	 * @return
	 */
	public boolean exist(AdminUser exist, AdminUser beside) {
		return adminUserDao.existBeside(exist, beside);
	}

	public AdminUser findByUserName(String userName) {
		QAdminUser user = new QAdminUser("adminUser");
		return adminUserDao.findOne(user.userName.eq(userName).and(user.deleted.ne(true)));
	}

	public AdminUser findById(String id) {
		QAdminUser user = new QAdminUser("adminUser");
		return adminUserDao.findOne(user.id.eq(id).and(user.deleted.ne(true)));
	}

	public void saveOnlyAdminUser(AdminUser user) {
		adminUserDao.save(user);
	}

	public boolean exist(String id, String pwd) {
		QAdminUser qAdminUser = new QAdminUser("qAdminUser");

		return adminUserDao.count(qAdminUser.id.eq(id).and(qAdminUser.pwd.eq(pwd)).and(qAdminUser.deleted.ne(true))) > 0 ? true : false;
	}

}