package org.springframework.samples.petclinic.redis;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:spring/tools-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("spring-data-jpa")
public class Example {

    // inject the actual template
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    @Qualifier("cacheManager")
    private CacheManager cacheManger;
    
    // inject the template as ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations
    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;

    public void addLink(String userId, URL url) {
//        listOps.leftPush(userId, url.toExternalForm());
        // or use template directly
        redisTemplate.boundListOps(userId).leftPush(url.toExternalForm());
    }
    
    @Test
    public void testAddLink() throws MalformedURLException{
    	this.addLink("test2", new URL("http://www.naver.com/test1url"));
    }
    
    @Cacheable(value="redis4" , key="#id" )
    public void cacheManger(String id){
    	System.out.println("test");
    }
    
    @Test
    public void testCacheManger(){
    	this.cacheManger("a");
    }
    
    
    public static void main(String[] args) {
		Example e = new Example();
		e.cacheManger("redis2");
		
		String s = "getId";
		System.out.println(s.getBytes());
	}
}
