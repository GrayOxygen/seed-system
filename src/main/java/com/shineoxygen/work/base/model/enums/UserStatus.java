package com.shineoxygen.work.base.model.enums;

import com.shineoxygen.work.base.model.Labeled;

/**
 * 用户状态
 *
 * @author
 */
public enum UserStatus implements Labeled {
	NORMAL("正常"), TOBEACTIVATED("待激活"), LOCKED("已锁定"), DISABLED("已禁用"), EXPIRED("已过期");

	private String label;

	private UserStatus(String label) {
		this.label = label;
	}

	/**
	 * 获取对用户更友好的标签用以显示
	 */
	@Override
	public String getLabel() {
		return label;
	}

}
