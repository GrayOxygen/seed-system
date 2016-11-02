package com.shineoxygen.work.tdd.junit.repo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shineoxygen.work.admin.model.AdminUserRole;
import com.shineoxygen.work.admin.model.Permission;
import com.shineoxygen.work.admin.model.Role;
import com.shineoxygen.work.admin.model.RolePermission;
import com.shineoxygen.work.admin.repo.AdminUserRoleRepo;
import com.shineoxygen.work.admin.repo.PermissionRepo;
import com.shineoxygen.work.admin.repo.RolePermissionRepo;
import com.shineoxygen.work.admin.repo.RoleRepo;
import com.shineoxygen.work.tdd.config.MongodbConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongodbConfig.class })
@SuppressWarnings("all")
public class UserRolePermissionRepoTest {
	@Autowired
	private PermissionRepo permissionRepo;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private RolePermissionRepo rolePermissionRepo;
	@Autowired
	private AdminUserRoleRepo adminUserRoleRepo;

	@Test
	public void save() {
		Permission perm = new Permission();
		perm.setName("主页面");
		perm.setShowOrder(1);
		perm.setUrl("admin:index");
		permissionRepo.save(perm);

		perm = new Permission();
		perm.setName("用户管理");
		perm.setShowOrder(1);
		perm.setUrl("admin:adminUsers:*");
		permissionRepo.save(perm);

		perm = new Permission();
		perm.setName("用户管理-查看用户");
		perm.setShowOrder(1);
		perm.setUrl("admin:adminUsers:read");
		permissionRepo.save(perm);

		perm = new Permission();
		perm.setName("角色管理");
		perm.setShowOrder(1);
		perm.setUrl("admin:roles:*");
		permissionRepo.save(perm);

		perm = new Permission();
		perm.setName("权限查询");
		perm.setShowOrder(1);
		perm.setUrl("admin:permissions:read");
		permissionRepo.save(perm);
		//
	}

	@Test
	public void userRolePerm() {
		// readPerm用户id 5814a391241869cb751f5bd3

		// 创建一个admin角色，一个用户查看角色
		Role admin = new Role();
		admin.setBuildin(true);
		admin.setName("admin");
		roleRepo.save(admin);
		Role userLook = new Role();
		userLook.setBuildin(true);
		userLook.setName("用户查看");
		roleRepo.save(userLook);
		// 用户角色关联
		AdminUserRole adminUserRole = new AdminUserRole();
		adminUserRole.setAdminUserId("5814a391241869cb751f5bd3");
		adminUserRole.setRoleId(userLook.getId());
		adminUserRoleRepo.save(adminUserRole);

		// 角色 权限关联
		RolePermission rolePermission = new RolePermission();

		List<Permission> allPerms = permissionRepo.findAll();
		for (Permission perm : allPerms) {
			rolePermission = new RolePermission();
			rolePermission.setRoleId(admin.getId());
			rolePermission.setPermissionId(perm.getId());
			rolePermissionRepo.save(rolePermission);
		}
		rolePermission = new RolePermission();
		rolePermission.setRoleId(userLook.getId());
		rolePermission.setPermissionId("58149e0e2418b918be6029bb");
		rolePermissionRepo.save(rolePermission);
		rolePermission = new RolePermission();
		rolePermission.setRoleId(userLook.getId());
		rolePermission.setPermissionId("58149e0d2418b918be6029b9");
		rolePermissionRepo.save(rolePermission);

	}
}
