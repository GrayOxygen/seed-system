package com.shineoxygen.work.base.model.bootstraptable;

public class Order {
	// 表格列下标，从0开始向右依次加一
	private Integer column;
	// asc or desc
	private String dir = "asc";

	// 属性名称
	private String property ;

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
