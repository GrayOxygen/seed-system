package com.shineoxygen.work.base.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.shineoxygen.work.base.utils.annotation.ExtendParent;

@SuppressWarnings("serial")
@ExtendParent(extendParentField=true)
public abstract class BaseEntity implements Serializable {
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = StringUtils.isBlank(id) ? null : id;
	}

	@JSONField(serialize = false)
	public boolean isNew() {
		return id == null;
	}

	@Override
	public String toString() {
		return getClass().getName() + "@" + JSON.toJSONString(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BaseEntity))
			return false;
		final BaseEntity other = (BaseEntity) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
			else
				return super.equals(obj);
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		if (getId() == null) {
			return super.hashCode();
		} else {
			return this.getId().hashCode();
		}
	}

}
