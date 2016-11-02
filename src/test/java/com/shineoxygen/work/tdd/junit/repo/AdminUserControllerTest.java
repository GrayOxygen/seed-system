package com.shineoxygen.work.tdd.junit.repo;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shineoxygen.work.admin.controller.AdminUserMgrController;
import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.admin.service.AdminUserService;
import com.shineoxygen.work.base.model.ResultObject;
import com.shineoxygen.work.tdd.config.MongodbConfig;
import com.shineoxygen.work.tdd.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongodbConfig.class, WebConfig.class })
@SuppressWarnings("all")
public class AdminUserControllerTest {
//	@Autowired
//	private AdminUserMgrController adminUserMgrController;
//	@Autowired
//	private AdminUserService adminUserSrv;
//	private MockHttpServletRequest req;
//	private MockHttpServletResponse res;
//	private AdminUser testUser;
//
//	@Before
//	public void setUp() throws Exception {
//		req = new MockHttpServletRequest();
//		res = new MockHttpServletResponse();
//
//		// 测试数据
//		testUser = adminUserSrv.findByUserNameAndPwd("AdminUserControllerTest", "123123");
//		if (null == testUser) {
//			testUser = new AdminUser();
//			testUser.setEmail("AdminUserControllerTest@qq.com");
//			testUser.setUserName("AdminUserControllerTest");
//			testUser.setPhoneNum("13222221111");
//			testUser.setPwd(DigestUtils.md5Hex("123123"));
//			adminUserSrv.save(testUser);
//			System.out.println(testUser.getId());
//		}
//	}
//
//	@Test
//	public void add() throws UnsupportedEncodingException {
//		AdminUser adminUser = new AdminUser();
//		adminUser.setEmail("90@qq.com");
//		adminUser.setUserName("AdminUserControllerTest");
//		adminUser.setPhoneNum("13222221111");
//		adminUser.setPwd(DigestUtils.md5Hex("123123"));
//		ResultObject result = adminUserMgrController.add(adminUser);
//		System.out.println(result.getMessage());
//		Assert.assertFalse(result.isSuccess());
//	}
//
//	@Test
//	public void edit() throws UnsupportedEncodingException {
//		AdminUser adminUser = adminUserSrv.findById(testUser.getId());
//		adminUser.setEmail("90@qq.com");
//		adminUser.setUserName("AdminUserControllerTest");
//		adminUser.setPhoneNum("13222221111");
//		adminUser.setPwd(DigestUtils.md5Hex("123123"));
//		ResultObject result = adminUserMgrController.edit(adminUser);
//		System.out.println(result.getMessage());
//		Assert.assertFalse(result.isSuccess());
//	}

}
