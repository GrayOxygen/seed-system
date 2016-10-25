package com.shineoxygen.work.temp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shineoxygen.work.temp.dao.PersonDao;
import com.shineoxygen.work.temp.dao.impl.PersonDaoImpl;
import com.shineoxygen.work.temp.pojo.Person;

/**
 * 临时bean配置，如临时用来测试的bean
 * 
 * @author 王辉阳
 * @date 2016年10月21日 下午4:56:05
 */
@Configuration
public class TempBeanConfig {

	@Bean
	public Person person() {
		return new Person();
	}

	@Bean
	public PersonDao personDao() {
		return new PersonDaoImpl();
	}
	

}
