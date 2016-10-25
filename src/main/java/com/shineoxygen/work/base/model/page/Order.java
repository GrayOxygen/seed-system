package com.shineoxygen.work.base.model.page;

import java.io.Serializable;

public class Order implements Serializable {
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	private boolean asc;
	private String name;

	private Order(String name, boolean asc) {
		this.name = name;
		this.asc = asc;
	}

	public boolean isAsc() {
		return this.asc;
	}

	public String getName() {
		return this.name;
	}

	public static Order asc(String name) {
		return new Order(name, true);
	}

	public static Order desc(String name) {
		return new Order(name, false);
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + ((this.asc) ? 1231 : 1237);
		result = 31 * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (super.getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
			if (this.asc != other.asc)
				return false;
		} else if (!(this.name.equals(other.name))) {
			return false;
		}
		return true;
	}

	public String toString() {
		return this.name + ' ' + ((this.asc) ? "asc" : "desc");
	}
}