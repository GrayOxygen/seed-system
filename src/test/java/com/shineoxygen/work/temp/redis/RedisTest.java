package com.shineoxygen.work.temp.redis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author 王辉阳
 * @date 2016年10月23日 上午11:04:36
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RedisConfig.class })
public class RedisTest {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Resource(name = "stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void stringSet() {
		stringRedisTemplate.opsForValue().set("hello", "world");
	}

	@Test
	public void stringGet() {
		System.out.println(stringRedisTemplate.opsForValue().get("hello"));
	}

	@Test
	public void stringGetSet() {
		stringRedisTemplate.opsForValue().getAndSet("hello", "wo shi wanghy");
		System.out.println(stringRedisTemplate.opsForValue().get("hello"));
	}

	@Test
	public void HMSET() {
		stringRedisTemplate.opsForHash().put("skill", "back-end", "java");
		stringRedisTemplate.opsForHash().put("skill", "front-end", "jquery");
	}

	@Test
	public void pojoHMSET() {
		Bar bar = new Bar();
		bar.setId("ding");
		bar.setName("test bar");
		redisTemplate.opsForHash().put("dong", bar.getId(), bar);
	}

	@Test
	public void pojoHGET() {
		Bar bar = (Bar) redisTemplate.opsForHash().get("dong", "ding");
		System.out.println(bar.getId() + bar.getName());
	}

}
