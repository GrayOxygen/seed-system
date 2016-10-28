package com.shineoxygen.work.admin.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.admin.service.AdminUserService;
import com.shineoxygen.work.base.controller.BaseAdminController;
import com.shineoxygen.work.base.model.ResultObject;
import com.shineoxygen.work.base.model.bootstraptable.TablePage;
import com.shineoxygen.work.base.model.page.QueryCondition;

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
	public String listPage() {
		return "adminUsers/listPage";
	}

	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addPage() {
		return "adminUsers/addPage";
	}

	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public String editPage(@RequestParam("id") AdminUser model, ModelMap modelMap) {
		System.out.println(model);
		modelMap.put("model", model);
		return "adminUsers/editPage";
	}

	/**
	 * 分页查询
	 * 
	 * @param bytes
	 * @param req
	 * @param modelMap
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
//	@RequestMapping(value = "/list")
//	public @ResponseBody TablePage<AdminUser> list(@RequestBody byte[] bytes, HttpServletRequest req, ModelMap modelMap)
//			throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchFieldException {
//		return adminUserSrv.bdTableList(wrapSentParameters(bytes));
//	}
	@RequestMapping(value = "/list")
	public @ResponseBody TablePage<AdminUser> list(Pageable pageable,QueryCondition queryCondition, HttpServletRequest req, ModelMap modelMap)
			throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchFieldException {
		return adminUserSrv.bdTableList( pageable,queryCondition);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResultObject add(AdminUser model) {
		if (adminUserSrv.exist(AdminUser.empty(model.getUserName()), null)) {
			return ResultObject.errResult("用户名已被占用");
		}
		model.setPwd(DigestUtils.md5Hex(model.getPwd()));
		adminUserSrv.save(model);
		return ResultObject.sucResult("添加成功");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody ResultObject edit(AdminUser model) {
		if (StringUtils.isBlank(model.getId())) {
			return ResultObject.errResult("id不能为空");
		}
		if (adminUserSrv.exist(AdminUser.empty(model.getUserName()), AdminUser.emptyWizId(model.getId()))) {
			return ResultObject.errResult("用户名已被占用");
		}
		model.setPwd(DigestUtils.md5Hex(model.getPwd()));
		adminUserSrv.save(model);
		return ResultObject.sucResult("修改成功");
	}


	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public @ResponseBody ResultObject delete(String[] ids) {
		adminUserSrv.softDelete(ids);
		return ResultObject.sucResult("删除成功");
	}

}
