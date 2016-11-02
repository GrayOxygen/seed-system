package com.shineoxygen.work.admin.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.admin.service.AdminUserRolePermissionMgr;
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
@SuppressWarnings("all")
public class AdminUserMgrController extends BaseAdminController {
	private static final Logger logger = LogManager.getLogger(AdminUserMgrController.class);

	@Autowired
	private AdminUserService adminUserSrv;
	@Autowired
	private AdminUserRolePermissionMgr adminUserRolePermMgr;

	@RequestMapping(value = "/listPage")
	public String listPage() {
		return "adminUsers/listPage";
	}

	/**
	 * 跳转角色主页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/rolesListPage")
	public String rolesListPage(String adminUserId, ModelMap modelMap) {
		// 回显角色选项
		modelMap.put("ids", null != adminUserId ? adminUserRolePermMgr.findRoleIdsByAdminUserId(adminUserId) : new ArrayList(0));
		return "redirect:/roles/commonListPage";
	}

	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addPage() {
		return "adminUsers/addPage";
	}

	@RequestMapping(value = "/editPage", method = RequestMethod.GET)
	public String editPage(@RequestParam("id") AdminUser model, ModelMap modelMap) {
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
	// @RequestMapping(value = "/list")
	// public @ResponseBody TablePage<AdminUser> list(@RequestBody byte[] bytes,
	// HttpServletRequest req, ModelMap modelMap)
	// throws UnsupportedEncodingException, IllegalAccessException,
	// InvocationTargetException, SecurityException, NoSuchFieldException {
	// return adminUserSrv.bdTableList(wrapSentParameters(bytes));
	// }
	@RequestMapping(value = "/list")
	public @ResponseBody TablePage<AdminUser> list(Pageable pageable, QueryCondition queryCondition, HttpServletRequest req, ModelMap modelMap)
			throws UnsupportedEncodingException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchFieldException {
		return adminUserSrv.bdTableList(pageable, queryCondition);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResultObject add(AdminUser model, String[] rolesID) {
		if (adminUserSrv.exist(AdminUser.empty(model.getUserName()), null)) {
			return ResultObject.errResult("用户名已被占用");
		}
		model.setPwd(DigestUtils.md5Hex(model.getPwd()));
		adminUserSrv.save(model, rolesID);
		return ResultObject.sucResult("添加成功");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody ResultObject edit(AdminUser model, String[] rolesID) {
		if (StringUtils.isBlank(model.getId())) {
			return ResultObject.errResult("id不能为空");
		}
		if (adminUserSrv.exist(AdminUser.empty(model.getUserName()), AdminUser.emptyWizId(model.getId()))) {
			return ResultObject.errResult("用户名已被占用");
		}
		model.setPwd(DigestUtils.md5Hex(model.getPwd()));
		adminUserSrv.update(model, rolesID);
		return ResultObject.sucResult("修改成功");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public @ResponseBody ResultObject delete(String[] ids) {
		for (String id : ids) {
			if (true == adminUserSrv.findById(id).isBuildin()) {
				return ResultObject.errResult("无权删除系统内置用户");
			}
		}
		adminUserSrv.softDelete(ids);
		return ResultObject.sucResult("删除成功");
	}

	@RequestMapping("/updatePwd")
	public @ResponseBody ResultObject updatePwd(String id, String pwd, String newPwd, HttpServletRequest req) {
		// 只能自己修改自己的密码
		if (StringUtils.isBlank(id) || null == req.getSession().getAttribute("ADMINUSER")
				|| !StringUtils.equals(id, ((AdminUser) req.getSession().getAttribute("ADMINUSER")).getId())) {
			return ResultObject.errResult("你无权修改当前密码！");
		}
		if (StringUtils.isBlank(newPwd)) {
			return ResultObject.errResult("请输入新密码！");
		}

		// 校验老密码正确性
		if (!adminUserSrv.exist(id,DigestUtils.md5Hex(pwd))) {
			return ResultObject.errResult("旧密码输入错误！");
		}
		AdminUser user = adminUserSrv.findById(id);
		user.setPwd(DigestUtils.md5Hex(newPwd));
		adminUserSrv.saveOnlyAdminUser(user);
		return ResultObject.sucResult("您已修改密码！");
	}

	@RequestMapping("/updatePwdPage")
	public String updatePwdPage(ModelMap modelMap, HttpServletRequest req) {
		modelMap.put("adminUserId", null != req.getSession().getAttribute("ADMINUSER") ? ((AdminUser) req.getSession().getAttribute("ADMINUSER")).getId() : "");
		return "adminUsers/updatePwdPage";
	}

}
