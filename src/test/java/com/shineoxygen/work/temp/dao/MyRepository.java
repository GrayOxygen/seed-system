package com.shineoxygen.work.temp.dao;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {
	void del(String id, Class clazz);
}
