package com.shineoxygen.work.admin.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.shineoxygen.work.base.model.User;
@Document(collection="AdminUser")
public class AdminUser extends User {
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
