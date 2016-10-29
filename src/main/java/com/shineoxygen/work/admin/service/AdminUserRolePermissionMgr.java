package com.shineoxygen.work.admin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List<Permission> findAllPermissions() {
		QPermission permission = new QPermission("permission");
		return (List<Permission>) permissionRepo.findAll(permission.deleted.ne(true));
	}

	public List<Role> findAllRoles() {
		QRole role = new QRole("role");
		return (List<Role>) roleRepo.findAll(role.deleted.ne(true));
	}
}