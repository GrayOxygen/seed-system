package com.shineoxygen.work.admin.service;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.shineoxygen.work.admin.dao.AdminUserDao;
import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.base.model.bootstraptable.SentParameters;
import com.shineoxygen.work.base.model.bootstraptable.TablePage;

@Service
public class AdminUserService {
	private static final Logger logger = LogManager.getLogger(AdminUserService.class);
	@Autowired
	private AdminUserDao adminUserDao;

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
		return adminUserDao.findByUserNameAndPwd(userName, pwd);
	}

	public AdminUser findById(String id) {
		return adminUserDao.findOne(id);
	}

	public TablePage<AdminUser> tablePage(SentParameters sentParameters) {
		// sentParameters
		Query query = new Query();
		if (sentParameters.containsKey("userName")) {
			query = new Query(Criteria.where("userName").regex(Pattern.compile(".*" + sentParameters.getValue("userName") + ".*")));
		}

		return adminUserDao.tablePage(query, sentParameters, AdminUser.class);
	}
}