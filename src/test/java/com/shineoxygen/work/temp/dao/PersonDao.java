package com.shineoxygen.work.temp.dao;

import com.shineoxygen.work.temp.pojo.Person;

public interface PersonDao {
	public void create(Person p);

	public Person read(String id);

	public void update(Person p);

	public int delete(String id);
}
