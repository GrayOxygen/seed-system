package com.shineoxygen.work.tdd.config;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.shineoxygen.work.base.repo.impl.UndeletedMongoRepositoryImpl;

/**
 * 数据库配置
 * 
 * @author 王辉阳
 * @date 2016年10月21日 下午4:55:58
 */
@Configuration
@ComponentScan("com.shineoxygen.work.tdd")
@PropertySources(value = { @PropertySource(value = "classpath:mongodb.properties") })
// 开启Mongo Repository自定义功能，才能给repository或自己的dao自定义方法
// repositoryImplementationPostfix默认为Impl，表示提供自定义方法的repository实现的名字后缀，spring由此找到接口与实现对应关系
@EnableMongoRepositories(basePackages = { "com.shineoxygen.work.base.repo","com.shineoxygen.work.admin.repo" }, repositoryImplementationPostfix = "Impl", repositoryBaseClass = UndeletedMongoRepositoryImpl.class)
public class MongodbConfig {
	@Autowired
	private Environment env;

	/**
	 * 使用Environment获取配置文件数据，进而配置数据源
	 * 
	 * @return
	 * @throws UnknownHostException
	 * @throws NumberFormatException
	 */
	@Bean
	public MongoOperations mongoTemplate() throws NumberFormatException, UnknownHostException {
		ServerAddress addr = new ServerAddress(env.getProperty("mongodb.host"), Integer.parseInt(env.getProperty("mongodb.port")));
		MongoCredential credential = MongoCredential.createScramSha1Credential(env.getProperty("mongodb.username"), env.getProperty("mongodb.databasename"),
				env.getProperty("mongodb.password").toCharArray());
		MongoClient mongo = new MongoClient(addr, Arrays.asList(credential));
		return new MongoTemplate(mongo, env.getProperty("mongodb.databasename"));
	}

}
