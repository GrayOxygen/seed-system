package com.shineoxygen.work.admin.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shineoxygen.work.admin.model.Permission;
import com.shineoxygen.work.admin.service.AdminUserRolePermissionMgr;
import com.shineoxygen.work.base.controller.BaseAdminController;
import com.shineoxygen.work.base.model.ResultObject;
import com.shineoxygen.work.base.model.bootstraptable.TablePage;
import com.shineoxygen.work.base.model.page.QueryCondition;

/**
 * @author 王辉阳
 * @date 2016年10月24日 下午5:13:59
 * @Description 角色管理
 */
@Controller
@RequestMapping("/permissions")
public class PermissionController extends BaseAdminController {
	private static final Logger logger = LogManager.getLogger(PermissionController.class);

	@Autowired
	private AdminUserRolePermissionMgr adminUserRolePermMgr;

	/**
	 * 跳转主页面
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/listPage")
	public String listPage(ModelMap modelMap) {
		return "permissions/listPage";
	}

	/**
	 * 公用页面
	 * 
	 * @param ids
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/commonListPage")
	public String commonListPage(@RequestParam(value = "ids", required = false) String[] ids, ModelMap modelMap) {
		modelMap.put("ids", ids);
		return "permissions/commonListPage";
	}

	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addPage() {
		return "permissions/addPage";
	}

	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public String editPage(@RequestParam("id") Permission model, ModelMap modelMap) {
		modelMap.put("model", model);
		return "permissions/editPage";
	}

	@RequestMapping(value = "/commonList")
	public @ResponseBody TablePage<Permission> commonList(Pageable pageable, QueryCondition queryCondition, HttpServletRequest req, ModelMap modelMap)
			throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchFieldException {
		return adminUserRolePermMgr.bdTablePermList(pageable, queryCondition);
	}

	@RequestMapping(value = "/list")
	public @ResponseBody TablePage<Permission> list(Pageable pageable, QueryCondition queryCondition, HttpServletRequest req, ModelMap modelMap)
			throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchFieldException {
		return adminUserRolePermMgr.bdTablePermList(pageable, queryCondition);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResultObject add(Permission model) {
		adminUserRolePermMgr.savePermission(model);
		return ResultObject.sucResult("添加成功");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody ResultObject edit(Permission model) {
		if (StringUtils.isBlank(model.getId())) {
			return ResultObject.errResult("id不能为空");
		}

		if (adminUserRolePermMgr.existBuildinPermission(model.getId())) {
			return ResultObject.errResult("不能修改内建权限菜单");
		}
		adminUserRolePermMgr.savePermission(model);
		return ResultObject.sucResult("修改成功");
	}

}
