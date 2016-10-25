package com.shineoxygen.work.base.model.bootstraptable;

public class Column {
	private String data;
	private String name;
	private Boolean searchable;
	private Boolean orderable;
	private Search search = new Search();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSearchable() {
		return searchable;
	}

	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}

	public Boolean getOrderable() {
		return orderable;
	}

	public void setOrderable(Boolean orderable) {
		this.orderable = orderable;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Column [data=" + data + ", name=" + name + ", searchable=" + searchable + ", orderable=" + orderable + ", search=" + search + "]";
	}

}
