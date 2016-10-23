package com.shineoxygen.work.base.model;

import java.util.Date;

/**
 * 
 * @author 王辉阳
 * 
 * @date 2015年9月1日 下午1:43:33
 * 
 * @Description 日期范围对象(重构方式:Introduce Parameter Object)
 * 
 *              final属性只能由构造函数设值，保证参数对象的完整性
 */
public final class DateRange {
	private final Date startDate;
	private final Date endDate;

	public DateRange(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

}
