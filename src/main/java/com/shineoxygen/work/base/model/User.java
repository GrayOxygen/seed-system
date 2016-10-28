package com.shineoxygen.work.base.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

import com.alibaba.fastjson.annotation.JSONField;
import com.shineoxygen.work.base.utils.annotation.ExtendParent;

/**
 * 
 * @author 王辉阳
 * @date 2016年10月23日 下午6:18:54
 * @Description 所有用户父类
 */
@SuppressWarnings("serial")
@ExtendParent(extendParentField=true)
public abstract class User extends UnDeletedEntity {
	@JSONField(serialize = false)
	private String pwd; // 登录密码

	@Indexed(direction = IndexDirection.DESCENDING, background = true)
	private Date ctime = new Date(); // 创建日期
	private Date mtime; // 最后更新日期
	private Date atime; // 最后登录日期

	/**
	 * 获得显示用的唯一名
	 *
	 * @return
	 */
	public abstract String getUserName();

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = StringUtils.trim(pwd);
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

	public Date getAtime() {
		return atime;
	}

	public void setAtime(Date atime) {
		this.atime = atime;
	}

}
