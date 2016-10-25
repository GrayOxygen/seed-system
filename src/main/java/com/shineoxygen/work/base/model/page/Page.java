package com.shineoxygen.work.base.model.page;

import java.util.ArrayList;
import java.util.List;

public class Page<T> extends QueryParameter {
	private List<T> result = new ArrayList(0);

	private long totalCount = 0L;

	public Page() {
	}

	public Page(int pageSize) {
		setPageSize(pageSize);
	}

	public Page(int pageSize, boolean autoCount) {
		setPageSize(pageSize);
		setAutoCount(autoCount);
	}

	public List<T> getResult() {
		return this.result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		if (this.totalCount == 0L) {
			return 0;
		}
		int count = (int) (this.totalCount / this.pageSize);
		if (this.totalCount % this.pageSize > 0L) {
			++count;
		}
		return count;
	}

	public boolean isHasNext() {
		return (this.pageNo + 1 <= getTotalPages());
	}

	public int getNextPage() {
		if (isHasNext()) {
			return (this.pageNo + 1);
		}
		return this.pageNo;
	}

	public boolean isHasPre() {
		return (this.pageNo - 1 >= 1);
	}

	public int getPrePage() {
		if (isHasPre()) {
			return (this.pageNo - 1);
		}
		return this.pageNo;
	}
}