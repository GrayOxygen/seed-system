package com.shineoxygen.work.base.dao;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 所有dao实现该接口代替spring data提供的Repository等接口
 * 
 * 该公用类拥有spring data提供的通用repository的功能，也包含自定义的公用方法
 * 
 * @author 王辉阳
 * @date 2016年10月22日 下午10:28:25
 * @param <T>
 *            类
 * @param <ID>
 *            主键类型
 */
@NoRepositoryBean
public interface BaseMongoRepository<T, ID extends Serializable> extends MongoRepository<T, ID> {
	void del(String id, Class clazz);
}
