package com.shineoxygen.work.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.shineoxygen.work.dao.UserDao;
import com.shineoxygen.work.dao.UserDaoCustom;
import com.shineoxygen.work.pojo.User;

public class UserDaoImpl implements UserDaoCustom {
	// 作为一个普通的bean一样，可以引用其他bean，包括UserDao，也可以注入MongoOperations
	@Autowired
	private UserDao dao;

	@Override
	public void someCustomMethod(User user) {
		dao.save(user);
		System.out.println("some custom method");
	}

}
