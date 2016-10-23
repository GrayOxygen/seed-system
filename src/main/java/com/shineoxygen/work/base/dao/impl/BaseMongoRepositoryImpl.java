package com.shineoxygen.work.base.dao.impl;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import com.shineoxygen.work.base.dao.BaseMongoRepository;

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
}
