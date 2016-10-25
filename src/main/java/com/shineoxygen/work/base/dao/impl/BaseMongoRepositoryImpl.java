package com.shineoxygen.work.base.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.shineoxygen.work.base.dao.BaseMongoRepository;
import com.shineoxygen.work.base.model.bootstraptable.Order;
import com.shineoxygen.work.base.model.bootstraptable.SentParameters;
import com.shineoxygen.work.base.model.bootstraptable.TablePage;

public class BaseMongoRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T, ID> implements BaseMongoRepository<T, ID> {
	private MongoOperations mongoOperations;

	public BaseMongoRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
		this.mongoOperations = mongoOperations;
	}

	// all the custom method below
	@Override
	public void del(String id, Class clazz) {
		Query query = new Query(Criteria.where("_id").is(id));
		this.mongoOperations.remove(query, clazz);
	}

	@Override
	public TablePage<T> tablePage(Query query, SentParameters sentParameters, Class<T> clazz) {
		if (null == query) {
			return new TablePage<>();
		}

		List<Order> orders = sentParameters.getOrder();
		TablePage<T> table = new TablePage<>();

		// bootstrap datatable响应
		table.setDraw(sentParameters.getDraw());
		table.setRecordsFiltered(Integer.parseInt(String.valueOf(mongoOperations.count(query, clazz))));
		table.setRecordsTotal(Integer.parseInt(String.valueOf(mongoOperations.count(new BasicQuery("{ }"), clazz))));
		// 分页限制
		query.limit(sentParameters.getLength());
		query.skip(sentParameters.getStart());
		// 排序
		for (Order order : orders) {
			if (StringUtils.isNoneBlank(order.getProperty()))
				query.with(new Sort("asc".equalsIgnoreCase(order.getDir()) ? Direction.ASC : Direction.DESC, order.getProperty()));
		}

		List<T> results = mongoOperations.find(query, clazz);
		table.setData(results);
		return table;
	}
}
