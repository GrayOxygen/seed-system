package com.shineoxygen.work.temp.dao.impl;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.shineoxygen.work.temp.dao.MyRepository;
import com.shineoxygen.work.temp.pojo.Student;

public class MyRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T, ID> implements MyRepository<T, ID> {
	private MongoOperations mongoOperations;

	public MyRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
		this.mongoOperations = mongoOperations;
	}

	public void sharedCustomMethod(Student stu) {
		this.mongoOperations.save(stu);
	}

	@Override
	public void del(String id, Class clazz) {
		Query query = new Query(Criteria.where("_id").is(id));
		this.mongoOperations.remove(query, clazz);
	}
}