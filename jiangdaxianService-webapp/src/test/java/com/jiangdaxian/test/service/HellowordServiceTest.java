package com.jiangdaxian.test.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jiangdaxian.helloword.service.HellowordService;
import com.jiangdaxian.jdxtest.dao.JdxTestDao;
import com.jiangdaxian.jdxtest.entity.JdxTestEntity;
import com.jiangdaxian.jdxtest.mongo.JdxTestMongo;
import com.jiangdaxian.test.BaseTestCase;

public class HellowordServiceTest extends BaseTestCase {

	@Autowired
	private JdxTestDao jdxTestDao;
	@Autowired
	private HellowordService hellowordService;
	
	@Test
	public void testSayHello() {
		System.out.println("jdx:"+hellowordService.sayHello());
	}
	
	@Test
	public void testJdxTestDao() {
		try {
			JdxTestEntity jdxTestEntity = jdxTestDao.selectById(1L);
			System.out.println(JSONObject.toJSONString(jdxTestEntity));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Test
	public void testMongoSaveAndSelect() throws Exception {
		JdxTestMongo jdxTestMongo = new JdxTestMongo();
		jdxTestMongo.setId(20000L);
		jdxTestMongo.setName("20000name");
		int i = hellowordService.saveInMongo(jdxTestMongo);
		System.out.println("save:" + i);
		List<JdxTestMongo> list = hellowordService.getItemInfoInMongo(20000L);
		System.out.println("list:" + JSONObject.toJSONString(list));
	}
	
	@Test
	public void testRedis() throws Exception {
		List<JdxTestMongo> list = hellowordService.getItemInfoInMongo(20000L);
		hellowordService.saveInRedis("jdx",list.get(0));
		System.out.println(JSONObject.toJSONString(hellowordService.getInRedis("jdx")));
	}
}
