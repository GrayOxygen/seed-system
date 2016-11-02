package com.shineoxygen.work.admin.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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

import com.shineoxygen.work.admin.model.Role;
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
@RequestMapping("/roles")
public class RoleController extends BaseAdminController {
	private static final Logger logger = LogManager.getLogger(RoleController.class);

	@Autowired
	private AdminUserRolePermissionMgr adminUserRolePermMgr;

	@RequestMapping(value = "/listPage")
	public String listPage() {
		return "roles/listPage";
	}

	@RequestMapping(value = "/commonListPage")
	public String commonListPage(@RequestParam(value = "ids", required = false) String[] ids, ModelMap modelMap) {
		modelMap.put("ids", ids);
		return "roles/commonListPage";
	}

	/**
	 * 跳转权限主页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/permListPage")
	public String permListPage(String roleId, ModelMap modelMap) {
		modelMap.put("ids", null != roleId ? adminUserRolePermMgr.findPermIdsByRoleId(roleId) : new ArrayList(0));

		return "redirect:/permissions/commonListPage";
	}

	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addPage() {
		return "roles/addPage";
	}

	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public String editPage(@RequestParam("id") Role model, ModelMap modelMap) {
		modelMap.put("model", model);
		return "roles/editPage";
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
	@RequestMapping(value = "/list")
	public @ResponseBody TablePage<Role> list(Pageable pageable, QueryCondition queryCondition, HttpServletRequest req, ModelMap modelMap)
			throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchFieldException {
		return adminUserRolePermMgr.bdTableRoleList(pageable, queryCondition);
	}

	@RequestMapping(value = "/commonList")
	public @ResponseBody TablePage<Role> commonList(Pageable pageable, QueryCondition queryCondition, HttpServletRequest req, ModelMap modelMap)
			throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchFieldException {
		return adminUserRolePermMgr.bdTableRoleList(pageable, queryCondition);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResultObject add(Role model, String[] permsID) {
		if (StringUtils.isAnyBlank(permsID)) {
			return ResultObject.sucResult("请选择权限菜单！");
		}
		adminUserRolePermMgr.saveRole(model, permsID);
		return ResultObject.sucResult("添加成功");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody ResultObject edit(Role model, String[] permsID) {
		if (StringUtils.isBlank(model.getId())) {
			return ResultObject.errResult("id不能为空");
		}
		adminUserRolePermMgr.updateRole(model, permsID);
		return ResultObject.sucResult("修改成功");
	}

	/**
	 * 角色假删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public @ResponseBody ResultObject delete(String[] ids) {
		for (String id : ids) {
			if (true == adminUserRolePermMgr.findRoleById(id).isBuildin()) {
				return ResultObject.errResult("无权删除系统内置角色");
			}
		}
		adminUserRolePermMgr.softDeleteRoles(ids);
		return ResultObject.sucResult("删除成功");
	}

}
