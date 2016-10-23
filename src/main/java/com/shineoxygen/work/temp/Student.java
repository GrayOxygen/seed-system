package com.shineoxygen.work.temp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 演示类
 * @author 王辉阳
 * @date     2016年10月23日 上午11:03:27
 */
@Document(collection = "stu")
public class Student {
	@Id
	private String id;
	private String name;
	private String score;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
