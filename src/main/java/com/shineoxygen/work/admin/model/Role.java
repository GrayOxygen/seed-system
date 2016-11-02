package com.shineoxygen.work.admin.model;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shineoxygen.work.base.model.UnDeletedEntity;

@Document(collection = "Role")
@Entity
public class Role extends UnDeletedEntity {
	private String name; // 名称
	private boolean buildin; // 是否超管组
	private Date ctime;// create time

	public Role() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBuildin() {
		return buildin;
	}

	public void setBuildin(boolean buildin) {
		this.buildin = buildin;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}