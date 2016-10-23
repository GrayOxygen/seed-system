package com.shineoxygen.work.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shineoxygen.work.pojo.User;

/**
 * 拥有自定义方法的repository
 * 
 * @author 王辉阳
 * @date 2016年10月22日 下午9:25:24
 */
public interface UserDao extends MongoRepository<User, String>, UserDaoCustom {
	public List<User> findByName(String name);

	public List<User> findByJob(String job);

}
