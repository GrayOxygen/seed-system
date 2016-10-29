package com.shineoxygen.work.admin.model;

import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shineoxygen.work.base.model.UnDeletedEntity;

@Document(collection = "RolePermission")
@Entity // querydsl根据该注解生成Q对象
public class RolePermission extends UnDeletedEntity {
	private String roleId;
	private String permissionId;

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
