package com.shineoxygen.work.admin.model;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shineoxygen.work.base.model.UnDeletedEntity;
import com.shineoxygen.work.base.utils.JsonUtils;

@Document(collection = "Role")
@Entity
public class Role extends UnDeletedEntity {
	private String name; // 名称
	private boolean buildIn; // 是否超管组

	public Role() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBuildIn() {
		return buildIn;
	}

	public void setBuildIn(boolean buildIn) {
		this.buildIn = buildIn;
	}

}