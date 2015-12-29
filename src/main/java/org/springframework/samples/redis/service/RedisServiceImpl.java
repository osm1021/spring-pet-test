package org.springframework.samples.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	@Cacheable(value="getId" )
	public String get(String id) {
		// TODO Auto-generated method stub
		logger.info("id :: "+id);
		return id;
	}
	
	@Override
	@Cacheable(value="1" )
	public String get(String id, Integer num) {
		// TODO Auto-generated method stub
		logger.info("id :: {} || num :: {}",id,num);
		return id+num;
	}

	@Override
	public String testCache1(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
