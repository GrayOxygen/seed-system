package com.shineoxygen.work.base.model;

/**
 * 
 * @author 王辉阳
 * 
 * @date 2016年1月12日 上午9:57:14
 * 
 * @Description Bootstrap Validator 校验器需接受JSON结果集， 所对应该实体
 */
public class ValidateResult extends ResultObject {
	// true 校验通过
	private Boolean valid = false;

	public ValidateResult() {
		super();
	}

	public ValidateResult(boolean valid) {
		this.valid = valid;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

}
