package org.springframework.samples.redis.service;

public interface RedisService {
	public String get(String id);
	
	public String get(String id,Integer num);
	
	public String testCache1(String id);
}
