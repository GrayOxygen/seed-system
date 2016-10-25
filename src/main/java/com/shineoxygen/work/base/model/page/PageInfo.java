package com.shineoxygen.work.base.model.page;

public class PageInfo {

	public static String getAdminPagingNavigation(Page<?> page) {
		StringBuffer displayInfo = new StringBuffer();
		if (page.getTotalCount() == 0 || page.getTotalPages() == 1) {
			displayInfo.append("<ul class='fr'>");
		} else {
			displayInfo.append("<ul class='fr'>");
			displayInfo.append("<li>每页" + page.getPageSize() + "条 / </li>");
		}

		displayInfo.append("<li>共" + page.getTotalCount() + "条</li>");
		displayInfo.append("<li><a href=\"javascript:pageSize('10');\" >&nbsp;10&nbsp;</a>");
		displayInfo.append("<a href=\"javascript:pageSize('20');\" >&nbsp;20&nbsp;</a>");
		displayInfo.append("<a href=\"javascript:pageSize('30');\" >&nbsp;30&nbsp;</a>");
		displayInfo.append("<a href=\"javascript:pageSize('50');\" >&nbsp;50&nbsp;</a>");
		displayInfo.append("<li class='fir'><a href=\"javascript:pageInfo('0');\"><span>首页</span></a></li>");
		if (page.isHasPre()) {
			displayInfo.append("<li class='pre'><a href=\"javascript:pageInfo('" + (page.getPageNo() - 1) + "');\" ><span>上一页</span></a></li>");
		} else {
			displayInfo.append("<li class='pre'><a href=\"javascript:void(0)\" ><span>上一页</span></a></li>");
		}
		if (page.getPageNo() == 1) {
			displayInfo.append("<li class='cur'><a href=\"javascript:void(0);\"><span>1</span></a></li>");
		} else {
			displayInfo.append("<li><a href=\"javascript:pageInfo('1');\"><span>1</span></a></li>");
		}
		if (page.getTotalPages() > 2) {
			if (page.getPageNo() != 1 && page.getPageNo() != page.getTotalPages()) {
				displayInfo.append("<li class='cur'><a href=\"javascript:void(0);\"><span>" + page.getPageNo() + "</span></a></li>");
			} else {
				displayInfo.append("<li><a href=\"javascript:void(0);\"><span>...</span></a></li>");
			}
		}
		if (page.getTotalPages() > 1) {
			if (page.getPageNo() == page.getTotalPages()) {
				displayInfo.append("<li class='cur'><a href=\"javascript:void(0);\"><span>" + page.getTotalPages() + "</span></a></li>");
			} else {
				displayInfo.append("<li><a href=\"javascript:pageInfo('" + page.getTotalPages() + "');\"><span>" + page.getTotalPages() + "</span></a></li>");
			}
		}

		if (page.isHasNext()) {
			displayInfo.append("<li class='next'><a href=\"javascript:pageInfo('" + page.getNextPage() + "')\"><span>下一页</span></a></li>");
		} else {
			displayInfo.append("<li class='next'><a href=\"javascript:void(0);\"><span>下一页</span></a></li>");
		}
		displayInfo.append("<li class='last'><a href=\"javascript:pageInfo('" + page.getTotalPages() + "')\"><span>末页</span></a></li>");
		displayInfo.append("<li><span class='ma1'>转到</span><span><input id='numberSize' type='text' class='uspagesrk'/></span><span class='ma1'>页</span></li>");
		displayInfo.append("<li><input type='button' onclick=\"inputPageNo('" + page.getTotalPages() + "');\" value='确定' /></li></ul>");
		return displayInfo.toString();
	}

	public static String getCompanyPagingNavigation(Page<?> page) {

		StringBuffer displayInfo = new StringBuffer();
		displayInfo.append("<div class='col-md-5 col-sm-12'>");
		displayInfo.append("<div class='dataTables_info'>");
		if (page.getTotalCount() == 0) {
			displayInfo.append("总共" + page.getTotalCount() + "条");
		} else {
			int start = (page.getPageNo() - 1) * page.getPageSize() + 1;
			int end = page.getPageNo() == page.getTotalPages() ? (int) page.getTotalCount() : (start + page.getPageSize());
			displayInfo.append("显示  " + start + " to " + end + " 总共" + page.getTotalCount() + "条");
		}
		displayInfo.append("</div></div>");
		if (page.getTotalCount() > 0) {
			displayInfo.append("<div class='col-md-7 col-sm-12'>");
			displayInfo.append("<div class='dataTables_paginate paging_bootstrap'>");
			displayInfo.append("<div class='pagination' style='visibility: visible;'>");
			if (page.getPageNo() == 1) {
				displayInfo.append("<li class='prev disabled'>");
			} else {
				displayInfo.append("<li class='prev'>");
			}
			displayInfo.append("<a href=\"javascript:pageInfo('" + page.getPrePage() + "');\" title='Prev'>");
			displayInfo.append("<i class='fa fa-angle-left'></i></a></li>");

			int sp = 1;
			int ep = page.getTotalPages() > 10 ? 10 : page.getTotalPages();

			if (page.getPageNo() > 6) {
				sp = page.getPageNo() - 5;
				ep = sp + 9;
				if (ep > page.getTotalPages()) {
					ep = page.getTotalPages();
				}
			}
			for (int i = sp; i <= ep; i++) {
				if (i == page.getPageNo()) {
					displayInfo.append("<li class='active'>");
				} else {
					displayInfo.append("<li>");
				}
				displayInfo.append("<a href=\"javascript:pageInfo('" + i + "');\">" + i + "</a></li>");
			}

			if (page.getPageNo() == page.getTotalPages()) {
				displayInfo.append("<li class='next disabled'>");
			} else {
				displayInfo.append("<li class='next'>");
			}
			displayInfo.append("<a href=\"javascript:pageInfo('" + page.getNextPage() + "')\" title='Next'>");
			displayInfo.append("<i class='fa fa-angle-right'></i></a></li>");
			displayInfo.append("</ul></div></div>");
		}
		return displayInfo.toString();
	}
}