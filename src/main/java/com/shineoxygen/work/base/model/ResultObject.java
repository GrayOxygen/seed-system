package com.shineoxygen.work.base.model;

/**
 * 
 * @author 王辉阳
 * 
 * @date 2015年7月9日 上午9:10:07
 * 
 * @Description 结果消息，用于前台接收消息提示用户
 */
public class ResultObject {
	private boolean success = true;
	private Object obj;
	private String message;
	private Integer errorType;// 错误类型

	public ResultObject() {
	}

	public ResultObject(boolean success) {
		this.success = success;
	}

	public ResultObject(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	/**
	 * 
	 * @author 王辉阳
	 * @date 2016年1月12日 上午10:03:45
	 * @param valid
	 * @return 工厂方法
	 */
	public ValidateResult validateResult(Boolean valid) {
		return new ValidateResult(valid);
	}

	public Integer getErrorType() {
		return errorType;
	}

	public void setErrorType(Integer errorType) {
		this.errorType = errorType;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
