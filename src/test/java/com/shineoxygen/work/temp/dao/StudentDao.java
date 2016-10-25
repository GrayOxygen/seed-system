package com.shineoxygen.work.temp.dao;

import java.util.List;

import com.shineoxygen.work.temp.pojo.Student;

/**
 * 拥有自定义方法的repository 自定义的repository可代替了MongoRepository等spring
 * data提供的Repository，同时拥有自定义方法
 * 
 * @author 王辉阳
 * @date 2016年10月22日 下午9:25:24
 */
public interface StudentDao extends MyRepository<Student, String> {
	public List<Student> findByName(String name);

}
