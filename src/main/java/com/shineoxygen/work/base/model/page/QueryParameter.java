package com.shineoxygen.work.base.model.page;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class QueryParameter implements Serializable {
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final boolean DEFAULT_AUTO_COUNT = true;
	private Set<Order> orders = new LinkedHashSet(0);

	private Map<String, Object> filters = new LinkedHashMap();

	protected int pageNo = 1;
	protected int pageSize = 10;
	protected boolean autoCount = true;

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize > 0)
			this.pageSize = pageSize;
	}

	public boolean isPageSizeSetted() {
		return (this.pageSize > -1);
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		if (pageNo > 0)
			this.pageNo = pageNo;
	}

	public int getFirst() {
		if ((this.pageNo < 1) || (this.pageSize < 1)) {
			return -1;
		}
		return ((this.pageNo - 1) * this.pageSize);
	}

	public boolean isFirstSetted() {
		return ((this.pageNo > 0) && (this.pageSize > 0));
	}

	public boolean isFilterSetted() {
		return ((this.filters != null) && (!(this.filters.isEmpty())));
	}

	public boolean isOrderBySetted() {
		return ((this.orders != null) && (!(this.orders.isEmpty())));
	}

	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public void addOrder(Order order) {
		this.orders.add(order);
	}

	public void addOrder(String propertyName, boolean ascending) {
		if (ascending)
			this.orders.add(Order.asc(propertyName));
		else
			this.orders.add(Order.desc(propertyName));
	}

	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public Map<String, Object> getFilters() {
		return this.filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public boolean isFiltered() {
		return ((this.filters != null) && (this.filters.size() > 0));
	}

	public void addFilter(String propertyName, Object propertyValue) {
		this.filters.put(propertyName, propertyValue);
	}
}