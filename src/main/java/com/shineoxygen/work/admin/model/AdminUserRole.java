package com.shineoxygen.work.admin.model;

import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shineoxygen.work.base.model.UnDeletedEntity;

@Document(collection = "AdminUserRole")
@Entity // querydsl根据该注解生成Q对象
public class AdminUserRole extends UnDeletedEntity {
	private String adminUserId;
	private String roleId;

	public String getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
