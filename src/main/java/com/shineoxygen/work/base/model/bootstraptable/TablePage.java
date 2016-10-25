package com.shineoxygen.work.base.model.bootstraptable;

import java.util.List;

/**
 * 
 * @author 王辉阳
 * @date 2016年10月25日 下午9:54:44
 * @Description 前后端之间bootstrap datable以及查询参数交互的模型
 * 
 *              继承QueryCriteria待到需要返回请求参数时有用
 */
public class TablePage<T> extends QueryCriteria {
	// 响应给bootstrap datatable的要求参数
	private Integer draw;
	private Integer recordsTotal;
	private Integer recordsFiltered;
	private List<T> data;

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
