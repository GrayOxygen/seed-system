package com.shineoxygen.work.maintest.mongodriver;

import java.util.Arrays;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * 创建索引，提升查询速度
 * 
 * @author 王辉阳
 * @date 2016年10月23日 上午11:27:49
 */
public class MainIndex {

	public static void main(String args[]) {
		try {

			// To connect to mongodb server
			MongoCredential credential = MongoCredential.createCredential("wanghy", "guangdong", "111111".toCharArray());

			MongoClient mongoClient = new MongoClient(new ServerAddress("localhost"), Arrays.asList(credential));
			// To connect to mongodb server
			DB db = mongoClient.getDB("sampledb");

			DBCollection coll = db.getCollection("indextest");
			coll.createIndex(new BasicDBObject("username", 1));
			for (int ii = 0; ii < 10000; ii++) {

				BasicDBObject doc = new BasicDBObject("username", "王辉阳" + ii);

				coll.insert(doc);

			}
			BasicDBObject doc = new BasicDBObject("username", "1111");
			DBObject explainObject = coll.find(doc).explain();

			System.out.println(explainObject);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}