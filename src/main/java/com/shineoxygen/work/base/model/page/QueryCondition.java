package com.shineoxygen.work.base.model.page;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author 王辉阳
 * @date 2016年10月25日 下午10:07:33
 * @Description 查询条件
 */
public class QueryCondition {
	// 自定义的查询条件
	private Map<String, Object> filters = new LinkedHashMap<>();

	// 是否包含键
	public boolean containsNotNull(String key) {
		if (null == key || !filters.containsKey(key) || null == filters.get(key)) {
			return false;
		}
		return true;
	}

	/**
	 * 如果值为字符串，是空字符或者空格字符串则返回false
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsNotEmpty(String key) {
		if (!containsNotNull(key)) {
			return false;
		}
		if (filters.get(key) instanceof String && StringUtils.isBlank(filters.get(key).toString())) {
			return false;
		}
		return true;
	}

	// 获取查询条件值
	public Object getValue(String key) {
		if (containsNotNull(key)) {
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
