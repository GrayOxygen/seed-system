package com.shineoxygen.work.admin.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.shineoxygen.work.admin.model.AdminUserRole;
import com.shineoxygen.work.admin.model.Permission;
import com.shineoxygen.work.admin.model.QAdminUserRole;
import com.shineoxygen.work.admin.model.QPermission;
import com.shineoxygen.work.admin.model.QRole;
import com.shineoxygen.work.admin.model.QRolePermission;
import com.shineoxygen.work.admin.model.Role;
import com.shineoxygen.work.admin.model.RolePermission;
import com.shineoxygen.work.admin.repo.AdminUserRepo;
import com.shineoxygen.work.admin.repo.AdminUserRoleRepo;
import com.shineoxygen.work.admin.repo.PermissionRepo;
import com.shineoxygen.work.admin.repo.RolePermissionRepo;
import com.shineoxygen.work.admin.repo.RoleRepo;
import com.shineoxygen.work.base.model.bootstraptable.TablePage;
import com.shineoxygen.work.base.model.page.QueryCondition;

@Service
@SuppressWarnings("unchecked")
public class AdminUserRolePermissionMgr {
	private static final Logger logger = LogManager.getLogger(AdminUserRolePermissionMgr.class);
	@Autowired
	private AdminUserRepo adminUserDao;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PermissionRepo permissionRepo;
	@Autowired
	private AdminUserRoleRepo adminUserRoleRepo;
	@Autowired
	private RolePermissionRepo rolePermissionRepo;

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<AdminUserRole> findAdminUserRoles(String userId) {
		QAdminUserRole adminUserRole = new QAdminUserRole("adminUserRole");
		return (List<AdminUserRole>) adminUserRoleRepo.findAll(adminUserRole.adminUserId.eq(userId));
	}

	/**
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RolePermission> findRolePermissions(String roleId) {
		QRolePermission rolePermission = new QRolePermission("rolePermission");
		return (List<RolePermission>) rolePermissionRepo.findAll(rolePermission.roleId.eq(roleId));
	}

	/**
	 * 
	 * @param permissionIds
	 * @return
	 */
	public List<Permission> findPermissionsFromPermission(List<String> permissionIds) {
		QPermission permission = new QPermission("permission");
		return (List<Permission>) permissionRepo.findAll(permission.id.in(permissionIds));
	}

	/**
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<Role> findRolesFromRole(List<String> roleIds) {
		QRole role = new QRole("role");
		return (List<Role>) roleRepo.findAll(role.id.in(roleIds));
	}

	/**
	 * 获取用户的所有角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<Role> findRolesByUserId(String userId) {
		List<AdminUserRole> adminUserRoles = findAdminUserRoles(userId);
		return findRolesFromRole((List<String>) CollectionUtils.collect(adminUserRoles, new Transformer() {
			@Override
			public Object transform(Object input) {
				return ((AdminUserRole) input).getRoleId();
			}
		}));
	}

	/**
	 * 获取指定角色所有的权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Permission> findPermissionsByRoleId(String roleId) {
		List<RolePermission> rolePermissions = findRolePermissions(roleId);
		return findPermissionsFromPermission((List<String>) CollectionUtils.collect(rolePermissions, new Transformer() {
			@Override
			public Object transform(Object input) {
				return ((RolePermission) input).getPermissionId();
			}
		}));
	}

	/**
	 * 获取多个角色的所有权限
	 * 
	 * @param roles
	 * @return
	 */
	public List<Permission> findPermissionsByRoles(List<Role> roles) {
		Set<Permission> set = new HashSet<>();
		for (Role role : roles) {
			if (StringUtils.isNoneBlank(role.getId())) {
				set.addAll(findPermissionsByRoleId(role.getId()));
			}
		}
		return new ArrayList<>(set);
	}

	/**
	 * 获取所有权限菜单
	 * 
	 * @return
	 */
	public List<Permission> findAllPermissions() {
		QPermission permission = new QPermission("permission");
		return (List<Permission>) permissionRepo.findAll(permission.deleted.ne(true));
	}

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	public List<Role> findAllRoles() {
		QRole role = new QRole("role");
		return (List<Role>) roleRepo.findAll(role.deleted.ne(true));
	}

	/**
	 * 分页查询角色
	 * 
	 * @param pageable
	 * @param queryCondition
	 * @return
	 */
	public TablePage<Role> bdTableRoleList(Pageable pageable, QueryCondition queryCondition) {
		QRole user = new QRole("role");
		BooleanExpression expression = user.deleted.ne(true);
		if (queryCondition.containsNotEmpty("name")) {
			expression = expression.and(user.name.like("%" + queryCondition.getValue("name") + "%"));
		}
		return roleRepo.bdTableList(expression, pageable);
	}

	/**
	 * 保存角色，同时保存对应的权限关联
	 * 
	 * @param model
	 * @param permissionIds
	 */
	public void saveRole(Role model, String[] permissionIds) {
		roleRepo.save(model);

		for (String permissionId : permissionIds) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(model.getId());
			rolePermission.setPermissionId(permissionId);
			rolePermissionRepo.save(rolePermission);
		}

	}

	/**
	 * 查指定角色
	 * 
	 * @param id
	 * @return
	 */
	public Role findRoleById(String id) {
		QRole role = new QRole("role");
		return roleRepo.findOne(role.id.eq(id).and(role.deleted.ne(true)));
	}

	/**
	 * 软删除角色以及permission关联
	 * 
	 * @param ids
	 */
	public void softDeleteRoles(String[] ids) {
		roleRepo.softDelete(ids);

		softDeleteRolePermission(ids);

	}

	/**
	 * 假删除RolePermission关联
	 * 
	 * @param roleIds
	 *            角色id数组
	 */
	private void softDeleteRolePermission(String[] roleIds) {
		QRolePermission qRolePermission = new QRolePermission("rolePermission");
		List<RolePermission> rolePermissions = (List<RolePermission>) rolePermissionRepo.findAll(qRolePermission.roleId.in(roleIds));
		for (RolePermission rolePermission : rolePermissions) {
			rolePermission.setDeleted(true);
			rolePermissionRepo.save(rolePermission);
		}
	}

	/**
	 * 分页查询权限菜单
	 * 
	 * @param pageable
	 * @param queryCondition
	 * @return
	 */
	public TablePage<Permission> bdTablePermList(Pageable pageable, QueryCondition queryCondition) {
		QPermission perm = new QPermission("permission");
		BooleanExpression expression = perm.deleted.ne(true);
		if (queryCondition.containsNotEmpty("name")) {
			expression = expression.and(perm.name.like("%" + queryCondition.getValue("name") + "%"));
		}
		return permissionRepo.bdTableList(expression, pageable);
	}

	/**
	 * 获取角色的所有权限菜单id
	 * 
	 * @param roleId
	 * @return
	 */
	public List<String> findPermIdsByRoleId(String roleId) {
		return (List<String>) CollectionUtils.collect(findRolePermsByRoleId(roleId), new Transformer() {
			@Override
			public Object transform(Object input) {
				return ((RolePermission) input).getPermissionId();
			}
		});
	}

	public List<RolePermission> findRolePermsByRoleId(String roleId) {
		QRolePermission qRolePermission = new QRolePermission("qRolePermission");

		return (List<RolePermission>) rolePermissionRepo.findAll(qRolePermission.roleId.eq(roleId).and(qRolePermission.deleted.ne(true)));
	}

	/**
	 * 修改角色以及角色和权限菜单的关系
	 * 
	 * @param model
	 * @param permsID
	 *            权限菜单的id数组
	 */
	public void updateRole(Role model, String[] permsID) {
		roleRepo.save(model);
		// 无用关联假删
		softDeleteRolePermission(new String[] { model.getId() });
		// 插入新关联
		for (String permissionId : permsID) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(model.getId());
			rolePermission.setPermissionId(permissionId);
			rolePermissionRepo.save(rolePermission);
		}

	}

	public List<String> findRoleIdsByAdminUserId(String adminUserId) {
		QAdminUserRole qAdminUserRole = new QAdminUserRole("adminUserRole");

		return (List<String>) CollectionUtils
				.collect((List<AdminUserRole>) adminUserRoleRepo.findAll(qAdminUserRole.adminUserId.eq(adminUserId).and(qAdminUserRole.deleted.ne(true))), new Transformer() {
					@Override
					public Object transform(Object input) {
						return ((AdminUserRole) input).getRoleId();
					}
				});
	}

	public boolean existBuildinPermission(String id) {
		QPermission qPermission = new QPermission("qPermission");
		return permissionRepo.count(qPermission.id.eq(id).and(qPermission.buildin.eq(true)).and(qPermission.deleted.ne(true))) > 0 ? true : false;
	}

	public void savePermission(Permission model) {
		permissionRepo.save(model);
	}
}