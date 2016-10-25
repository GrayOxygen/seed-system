package com.shineoxygen.work.temp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.WriteResult;
import com.shineoxygen.work.temp.dao.PersonDao;
import com.shineoxygen.work.temp.pojo.Person;

public class PersonDaoImpl implements PersonDao {
	@Autowired
	private MongoOperations mongoOps;
	private static final String PERSON_COLLECTION = "Person";

	public PersonDaoImpl() {
	}

	@Override
	public void create(Person p) {
		this.mongoOps.insert(p, PERSON_COLLECTION);
	}

	@Override
	public Person read(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return this.mongoOps.findOne(query, Person.class, PERSON_COLLECTION);
	}

	@Override
	public void update(Person p) {
		this.mongoOps.save(p, PERSON_COLLECTION);
	}

	@Override
	public int delete(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		WriteResult result = this.mongoOps.remove(query, Person.class, PERSON_COLLECTION);
		return result.getN();
	}
}
