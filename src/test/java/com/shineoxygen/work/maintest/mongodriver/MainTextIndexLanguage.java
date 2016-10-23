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
 * 创建全文索引，不支持中文
 * 
 * @author 王辉阳
 * @date 2016年10月23日 上午11:28:35
 */
public class MainTextIndexLanguage {

	public static void main(String args[]) {
		try {

			// To connect to mongodb
			MongoCredential credential = MongoCredential.createCredential("wanghy", "guangdong", "111111".toCharArray());

			MongoClient mongoClient = new MongoClient(new ServerAddress("localhost"), Arrays.asList(credential));
			// To connect to mongodb server
			DB db = mongoClient.getDB("sampledb");

			DBCollection coll = db.getCollection("textitems");

			// coll.createIndex(new BasicDBObject("username", "text"));

			BasicDBObject search = new BasicDBObject("$search", "1111");

			DBObject textSearch = new BasicDBObject("$text", search.append("$language", "english"));
			int matchCount = coll.find(textSearch).count();
			System.out.println("Text search matches: " + matchCount);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}