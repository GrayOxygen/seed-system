package com.shineoxygen.work.temp.maintest;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.shineoxygen.work.temp.pojo.Person;

/**
 * 连接数据库，使用spring data的MongoOperations接口操作数据库
 * 
 * @author 王辉阳
 * @date 2016年10月23日 上午11:28:54
 */
public class PersonTest {

	public static final String DB_NAME = "guangdong";
	public static final String PERSON_COLLECTION = "Person";
	public static final String MONGO_HOST = "127.0.0.1";
	public static final int MONGO_PORT = 27017;
	public static final String USER_NAME = "wanghy";
	public static final String PASSWORD = "111111";

	public static void main(String[] args) {
		try {
			ServerAddress addr = new ServerAddress(MONGO_HOST, MONGO_PORT);
			MongoCredential credential = MongoCredential.createScramSha1Credential(USER_NAME, DB_NAME, PASSWORD.toCharArray());
			List<MongoCredential> list = new ArrayList<>();
			list.add(credential);
			MongoClient mongo = new MongoClient(addr, list);
			MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME);
			System.out.println(mongo.getUsedDatabases());
			System.out.println(mongoOps.getCollectionName(Person.class));
			Person p = new Person("13", "PankajKr", "Bangalore, India");
			mongoOps.insert(p, PERSON_COLLECTION);
			Person p1 = mongoOps.findOne(new Query(Criteria.where("name").is("PankajKr")), Person.class, PERSON_COLLECTION);

			System.out.println(p1);

			mongoOps.dropCollection(PERSON_COLLECTION);
			mongo.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
