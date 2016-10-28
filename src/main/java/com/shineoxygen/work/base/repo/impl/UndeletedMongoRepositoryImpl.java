package com.shineoxygen.work.base.repo.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;

import com.querydsl.core.types.Predicate;
import com.shineoxygen.work.admin.model.AdminUser;
import com.shineoxygen.work.base.model.bootstraptable.Order;
import com.shineoxygen.work.base.model.bootstraptable.SentParameters;
import com.shineoxygen.work.base.model.bootstraptable.TablePage;
import com.shineoxygen.work.base.model.page.Page;
import com.shineoxygen.work.base.repo.UndeletedMongoRepository;
import com.shineoxygen.work.base.utils.ReflectionUtils;

@SuppressWarnings("all")
public class UndeletedMongoRepositoryImpl<T, ID extends Serializable> extends QueryDslMongoRepository<T, ID> implements UndeletedMongoRepository<T, ID> {
	private MongoOperations mongoOperations;
	private Class<T> entityClass;
	private Field[] fields;

	public UndeletedMongoRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations) {
		super(entityInformation, mongoOperations);
		this.mongoOperations = mongoOperations;
		this.entityClass = entityInformation.getJavaType();
		this.fields = ReflectionUtils.findAllFieldArray(entityClass);
	}

	// all the custom method below
	@Override
	public void del(String id, Class clazz) {
		Query query = new Query(Criteria.where("_id").is(id));
		this.mongoOperations.remove(query, clazz);
	}

	@Override
	public TablePage<T> bdTableList(Predicate predicate, Pageable pageable) {
		return new TablePage<>(findAll(predicate, pageable), (int) count(predicate));
	}

	@Override
	public void softDelete(String id) {
		Update update = new Update();
		update.set("deleted", true);
		this.mongoOperations.updateFirst(new Query(Criteria.where("_id").is(id)), update, entityClass);

	}

	@Override
	public void softDelete(String[] ids) {
		Update update = new Update();
		update.set("deleted", true);
		this.mongoOperations.updateMulti(new Query(Criteria.where("_id").in(ids)), update, entityClass);
	}

	@Override
	public void softDelete(List<String> ids) {
		softDelete(ids.toArray(new String[0]));
	}

	@Override
	public void softDelete(Set<String> ids) {
		Update update = new Update();
		update.set("deleted", true);
		mongoOperations.updateFirst(new Query(Criteria.where("_id").in(ids)), update, entityClass);
	}

	@Override
	public void update(T entity) {
		try {
			Update update = new Update();
			for (Field field : fields) {
				String fieldName = field.getName();
				if (!StringUtils.equalsAny(fieldName, null, "id")) {
					update.set(fieldName, BeanUtils.getProperty(entity, fieldName));
				}
			}
			// 然后更新我们的对象
			mongoOperations.updateFirst(new Query(Criteria.where("_id").is(BeanUtils.getProperty(entity, "id")).andOperator(Criteria.where("deleted").ne(true))), update,
					entityClass);
		} catch (Exception e) {

		}
	}

	@Override
	public boolean exist(T existCondition) {
		return existBeside(existCondition, null);
	}

	@Override
	public boolean existBeside(T existCondition, T besideCondition) {
		this.fields = ReflectionUtils.findAllFieldArray(entityClass, "deleted");
		Query query = new Query();
		try {
			if (null != besideCondition) {
				for (Field field : fields) {
					Criteria criteria = new Criteria();
					String fieldName = field.getName();
					if (StringUtils.isNoneEmpty(BeanUtils.getProperty(besideCondition, fieldName))) {
						query.addCriteria(criteria.where(fieldName).ne(BeanUtils.getProperty(besideCondition, fieldName)));
					}
				}
			}

			if (null != existCondition) {
				for (Field field : fields) {
					Criteria criteria = new Criteria();
					String fieldName = field.getName();
					if (StringUtils.isNoneEmpty(BeanUtils.getProperty(existCondition, fieldName))) {
						query.addCriteria(criteria.where(fieldName).is(BeanUtils.getProperty(existCondition, fieldName)));
					}
				}
			}
		} catch (Exception e) {
		}
		return mongoOperations.count(query.addCriteria(Criteria.where("deleted").ne(true)), entityClass) > 0 ? true : false;
	}
	// @Override
	// public TablePage<T> tablePage(Query query, SentParameters sentParameters,
	// Class<T> clazz) {
	// if (null == query) {
	// return new TablePage<>();
	// }
	//
	// List<Order> orders = sentParameters.getOrder();
	// TablePage<T> table = new TablePage<>();
	//
	// // bootstrap datatable响应
	// table.setDraw(sentParameters.getDraw());
	// table.setRecordsFiltered(Integer.parseInt(String.valueOf(mongoOperations.count(query,
	// clazz))));
	// table.setRecordsTotal(Integer.parseInt(String.valueOf(mongoOperations.count(new
	// BasicQuery("{ }"), clazz))));
	// // 分页限制
	// query.limit(sentParameters.getLength());
	// query.skip(sentParameters.getStart());
	// // 排序
	// for (Order order : orders) {
	// if (StringUtils.isNoneBlank(order.getProperty()))
	// query.with(new Sort("asc".equalsIgnoreCase(order.getDir()) ?
	// Direction.ASC : Direction.DESC, order.getProperty()));
	// }
	//
	// List<T> results = mongoOperations.find(query, clazz);
	// table.setData(results);
	// return table;
	// return null;
	// }
}
