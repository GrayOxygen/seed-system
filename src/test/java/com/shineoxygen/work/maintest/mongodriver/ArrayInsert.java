package com.shineoxygen.work.maintest.mongodriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * 测试mongodb driver插入list
 * 
 * @author 王辉阳
 * @date 2016年10月22日 上午10:45:53
 */
public class ArrayInsert {

	private final static int PORT = 27017;
	private final static String HOST = "localhost";

	public static void main(String args[]) {
		try {
			// server：mongodb3之后认证机制采用SCRAM-SHA-1，之前采用MONGODB_CR，springdata已经帮我们自动区分了
			MongoCredential credential = MongoCredential.createCredential("wanghy", "guangdong", "111111".toCharArray());

			MongoClient mongoClient = new MongoClient(new ServerAddress(HOST), Arrays.asList(credential));

			DB db = mongoClient.getDB("sampledb");

			DBCollection coll = db.getCollection("javastuff");

			List<DBObject> kids = new ArrayList<>();
			kids.add(new BasicDBObject("name", "david"));
			kids.add(new BasicDBObject("name", "moka"));

			DBObject doc = new BasicDBObject("name", "john").append("age", 35).append("kids", kids).append("info",
					new BasicDBObject("email", "john@mail.com").append("phone", "876-134-667"));
			coll.insert(doc);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
