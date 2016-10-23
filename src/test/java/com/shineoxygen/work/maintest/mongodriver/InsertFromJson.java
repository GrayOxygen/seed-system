package com.shineoxygen.work.maintest.mongodriver;

import java.util.Arrays;

import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;
import com.shineoxygen.work.pojo.Customer;

/**
 * 将json数据插入到数据库
 * 
 * @author 王辉阳
 * @date 2016年10月22日 上午11:01:18
 */
public class InsertFromJson {
	public static void main(String args[]) {
		try {
			// To connect to mongodb server
			MongoCredential credential = MongoCredential.createCredential("wanghy", "guangdong", "111111".toCharArray());

			MongoClient mongoClient = new MongoClient(new ServerAddress("localhost"), Arrays.asList(credential));
			// To connect to mongodb server
			DB db = mongoClient.getDB("sampledb");

			DBCollection coll = db.getCollection("javastuff");

			System.out.println("Collection created successfully");

			Customer c = new Customer("john", 22, "john@gmail.com", "777-666-555");

			Gson gson = new Gson();
			String json = gson.toJson(c);

			DBObject dbObject = (DBObject) JSON.parse(json);

			coll.insert(dbObject);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}