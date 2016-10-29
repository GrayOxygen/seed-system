package com.shineoxygen.work.admin.model;

import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import com.shineoxygen.work.base.model.UnDeletedEntity;

@Document(collection = "Permission")
@Entity // querydsl根据该注解生成Q对象
public class Permission extends UnDeletedEntity {
	private String name;
	private String url; // 权限URL
	private boolean buildIn; // 是否内置
	private int showOrder; // 显示顺序

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public boolean isBuildIn() {
		return buildIn;
	}

	public void setBuildIn(boolean buildIn) {
		this.buildIn = buildIn;
	}

	public String toString() {
		return "id:" + getId() + ",name:" + name + ",url:" + url + ",showOrder:" + showOrder;
	}

}
