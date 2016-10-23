package com.shineoxygen.work.base.model;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 
 * @author 王辉阳
 * @date 2016年10月23日 下午6:17:42
 * @Description 假删除model
 */
@SuppressWarnings("all")
public abstract class UnDeletedEntity extends BaseEntity {
	@Indexed
	private Boolean deleted;

	/**
	 * 为节省MongoDB的空间,可以用null或者false表示未删除;true为已删除
	 */
	public boolean isDeleted() {
		return (deleted == null) ? false : deleted;
	}

	/**
	 * 为节省MongoDB的空间,为false时保存null;true保存true
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted ? true : null;
	}

}