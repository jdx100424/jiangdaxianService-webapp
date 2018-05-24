package com.jiangdaxian.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jiangdaxian.helloword.service.HellowordService;
import com.jiangdaxian.jdxtest.dao.JdxTestDao;
import com.jiangdaxian.jdxtest.entity.JdxTestEntity;
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
}
