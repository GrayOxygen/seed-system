package com.shineoxygen.work.base.model.bootstraptable;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author 王辉阳
 * @date 2016年10月25日 下午10:07:33
 * @Description 查询条件
 */
public class QueryCriteria {
	// 自定义的查询条件
	private Map<String, Object> filters = new LinkedHashMap<>();

	// 是否包含键
	public boolean containsKey(String key) {
		if (null == key) {
			return false;
		}
		return filters.containsKey(key);
	}

	// 获取查询条件值
	public Object getValue(String key) {
		if (containsKey(key)) {
			return filters.get(key);
		}
		return null;
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}
}
