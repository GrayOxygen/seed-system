package com.shineoxygen.work.base.model.bootstraptable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.shineoxygen.work.base.model.page.QueryCondition;

/**
 * 
 * @author 王辉阳
 * @date 2016年10月25日 下午3:40:01
 * @Description bootsrap datatable发送给服务端的请求数据
 */
public class SentParameters extends QueryCondition {
	// 请求次数
	private Integer draw;
	// 开始记录下标
	private Integer start = 0;
	// 一页记录数
	private Integer length = 10;
	// 全局搜索
	private Search search = new Search();
	// 列信息
	private List<Column> columns = new ArrayList<>();
	// 排序
	private List<Order> order = new ArrayList<>();
	// 定义一个datatable列与POJO的属性名之间的映射关系，便于排序
	private ColumnProperty columnProperty = new ColumnProperty();

	public void wrapColumns(Map<String, List<String>> columnMap) throws IllegalAccessException, InvocationTargetException {
		Iterator<Entry<String, List<String>>> iterator = columnMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<String>> entry = iterator.next();
			Column column = new Column();
			for (String str : entry.getValue()) {
				if (str.split("=").length > 1 && !"".equals(str.split("=")[1])) {// 确保有值
					BeanUtils.setProperty(column, str.split("=")[0], str.split("=")[1]);
				}
			}
			this.addColumn(column);
		}
	}

	public void wrapOrder(Map<String, List<String>> orderMap) throws IllegalAccessException, InvocationTargetException {
		Iterator<Entry<String, List<String>>> iterator = orderMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, List<String>> entry = iterator.next();
			Order order = new Order();
			for (String str : entry.getValue()) {
				if (str.split("=").length > 1 && !"".equals(str.split("=")[1])) {// 确保有值
					BeanUtils.setProperty(order, str.split("=")[0], str.split("=")[1]);
				}
			}
			this.addOrder(order);
		}
	}

	/**
	 * 转为spring data 的Order对象
	 * 
	 * @return
	 */
	public List<org.springframework.data.domain.Sort.Order> toSpringOrder() {
		List<org.springframework.data.domain.Sort.Order> destList = new ArrayList<>();
		for (Order order : this.order) {
			org.springframework.data.domain.Sort.Order temp = new org.springframework.data.domain.Sort.Order(Direction.fromString(order.getDir()), order.getProperty());
			destList.add(temp);
		}
		return destList;
	}

	public Pageable toSpringPage() {
		Sort sort = new Sort(this.toSpringOrder());
		return new PageRequest(this.getStart() / this.getLength(), this.getLength(), sort);
	}

	// datatable的列与属性名称对应关系
	public class ColumnProperty {
		Map<Integer, String> relations = new HashMap<>();

		public Map<Integer, String> getRelations() {
			return relations;
		}

		public void setRelations(Map<Integer, String> relations) {
			this.relations = relations;
		}

		public void addRelation(Integer key, String value) {
			this.relations.put(key, value);
		}

		public void wireRelation() {
			for (Order order : getOrder()) {
				if (relations.containsKey(order.getColumn())) {
					order.setProperty(relations.get(order.getColumn()));
				}
			}

		}
	}

	public void addColumn(Column column) {
		this.columns.add(column);
	}

	public void addOrder(Order order) {
		this.order.add(order);
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public ColumnProperty getColumnProperty() {
		return columnProperty;
	}

	public void setColumnProperty(ColumnProperty columnProperty) {
		this.columnProperty = columnProperty;
	}

}
