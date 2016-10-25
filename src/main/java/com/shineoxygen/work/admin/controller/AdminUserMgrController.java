package com.shineoxygen.work.admin.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.admin.service.AdminUserService;
import com.shineoxygen.work.base.controller.BaseAdminController;
import com.shineoxygen.work.base.model.ResultObject;
import com.shineoxygen.work.base.model.bootstraptable.SentParameters;
import com.shineoxygen.work.base.model.bootstraptable.TablePage;
import com.shineoxygen.work.base.model.page.Page;

/**
 * 用户管理
 * 
 * @author 王辉阳
 * @date 2016年10月24日 下午5:13:59
 * @Description
 */
@Controller
@RequestMapping("/adminUsers")
public class AdminUserMgrController extends BaseAdminController {
	private static final Logger logger = LogManager.getLogger(AdminUserMgrController.class);

	@Autowired
	private AdminUserService adminUserSrv;

	@RequestMapping(value = "/listPage")
	public String listPage(Page<AdminUser> page, ModelMap modelMap) {
		return "adminUsers/listPage";
	}

	@RequestMapping(value = "/list")
	public @ResponseBody TablePage<AdminUser> list(@RequestBody byte[] bytes, HttpServletRequest req, ModelMap modelMap)
			throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchFieldException {

		SentParameters sentParameters = wrapSentParameters(bytes);
		TablePage<AdminUser> table = adminUserSrv.tablePage(sentParameters);

		return table;
	}

	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addPage() {
		return "adminUsers/addPage";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody String add(AdminUser adminUser) {
		adminUserSrv.save(adminUser);
		return ResultObject.sucResultJSON("添加成功");
	}

	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public String editPage() {
		return "adminUsers/editPage";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody String edit(AdminUser adminUser) {
		// 校验id是否为空
		if (StringUtils.isBlank(adminUser.getId())) {
			return ResultObject.errResultJSON("id不能为空");
		}
		adminUserSrv.save(adminUser);
		return ResultObject.sucResultJSON("编辑成功");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public @ResponseBody String delete(String id) {
		return ResultObject.sucResultJSON("删除成功");
	}

}
