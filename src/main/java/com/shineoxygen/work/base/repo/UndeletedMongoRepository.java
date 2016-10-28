package com.shineoxygen.work.base.repo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.Predicate;
import com.shineoxygen.work.base.model.bootstraptable.TablePage;

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
public interface UndeletedMongoRepository<T, ID extends Serializable> extends MongoRepository<T, ID>, QueryDslPredicateExecutor<T> {
	void del(String id, Class clazz);

	void update(T entity);

	void softDelete(String id);

	void softDelete(String[] ids);

	void softDelete(List<String> ids);

	void softDelete(Set<String> ids);

	public boolean exist(T existCondition);

	/**
	 * existCondition和besidesCondition存在相同属性有值时，以besidesCondition为准
	 * 
	 * @param existCondition
	 *            以非空属性作为等于条件
	 * @param besidesCondition
	 *            以非空属性作为不等于$ne条件
	 * @return
	 */
	public boolean existBeside(T existCondition, T besidesCondition);

	TablePage<T> bdTableList(Predicate predicate, Pageable pageable);

}
