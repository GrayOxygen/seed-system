package com.shineoxygen.work.dao;

import com.shineoxygen.work.pojo.Person;

public interface PersonDao {
	public void create(Person p);

	public Person read(String id);

	public void update(Person p);

	public int delete(String id);
}
