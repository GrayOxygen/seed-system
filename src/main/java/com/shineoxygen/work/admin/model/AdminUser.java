package com.shineoxygen.work.admin.model;

import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shineoxygen.work.base.model.User;
import com.shineoxygen.work.base.model.enums.UserStatus;

@Document(collection = "AdminUser")
@Entity
public class AdminUser extends User {
	@Indexed
	private String userName;
	private UserStatus userStatus = UserStatus.NORMAL;
	private String phoneNum;
	private String email;

	public static AdminUser empty() {
		AdminUser user = new AdminUser();
		user.setUserStatus(null);
		user.setCtime(null);
		return user;
	}

	public static AdminUser empty(String userName) {
		AdminUser user = new AdminUser().empty();
		user.setUserName(userName);
		return user;
	}

	public static AdminUser emptyWizId(String id) {
		AdminUser user = new AdminUser().empty();
		user.setId(id);
		return user;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
