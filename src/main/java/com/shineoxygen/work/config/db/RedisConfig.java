package com.shineoxygen.work.config.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置
 * 
 * @author 王辉阳
 * @date 2016年10月21日 下午4:55:58
 */
@Configuration
@PropertySources(value = { @PropertySource(value = "classpath:redis.properties") })
public class RedisConfig {
	@Autowired
	private Environment env;

	/**
	 * redis connector：jedis，连接工厂，设置地址端口，连接池等
	 * 
	 * @return
	 */
	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(Integer.parseInt(env.getProperty("redis.maxIdle")));
		jedisPoolConfig.setMaxTotal(Integer.parseInt(env.getProperty("redis.maxTotal")));
		jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(env.getProperty("redis.maxWaitMillis")));
		jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("redis.testOnBorrow")));
		redisConnectionFactory.setPoolConfig(jedisPoolConfig);
		redisConnectionFactory.setHostName(env.getProperty("redis.host"));
		redisConnectionFactory.setPort(Integer.parseInt(env.getProperty("redis.port")));
		redisConnectionFactory.setTimeout(Integer.parseInt(env.getProperty("redis.timeout")));
		redisConnectionFactory.setUsePool(Boolean.parseBoolean(env.getProperty("redis.usePool")));
		redisConnectionFactory.setDatabase(Integer.parseInt(env.getProperty("redis.database")));
		return redisConnectionFactory;
	}

	/**
	 * redis模板接口，key是readable的
	 * 
	 * @return
	 */
	@Bean
	@Primary // 优先注入
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		// ensure key is readable
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());

		return redisTemplate;
	}

	/**
	 * string redis模板接口：key和value都是字符串存储，都是可读的
	 * 
	 * @return
	 */
	@Bean
	public StringRedisTemplate stringRedisTemplate() {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}

	/**
	 * redis缓存管理
	 * 
	 * @return
	 */
	@Bean
	public RedisCacheManager redisCacheManager() {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());

		// Number of seconds before expiration. Defaults to unlimited (0)
		cacheManager.setDefaultExpiration(300);
		return cacheManager;
	}

}
