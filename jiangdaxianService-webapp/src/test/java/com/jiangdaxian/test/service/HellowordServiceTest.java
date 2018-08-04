package com.jiangdaxian.test.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jiangdaxian.helloword.service.HellowordService;
import com.jiangdaxian.jdxtest.dao.JdxTestDao;
import com.jiangdaxian.jdxtest.entity.JdxTestEntity;
import com.jiangdaxian.jdxtest.mongo.JdxTestMongo;
import com.jiangdaxian.mybatis.masterandslave.MasterAndSlaveDataSourceHolder;
import com.jiangdaxian.mybatis.pagelimit.PageLimitBounds;
import com.jiangdaxian.test.BaseTestCase;

public class HellowordServiceTest extends BaseTestCase {

	@Autowired
	private JdxTestDao jdxTestDao;
	@Autowired
	private HellowordService hellowordService;

	@Test
	public void testSelectElasticsearch() {
		hellowordService.selectElasticsearch();
	}
	
	@Test
	public void testSayHello() {
		System.out.println("jdx:" + hellowordService.sayHello());
	}

	@Test
	public void testJdxTestDaoLimit() {
		try {
			PageLimitBounds p = new PageLimitBounds();
			MasterAndSlaveDataSourceHolder.putDataSourceMaster();
			List<JdxTestEntity> list = jdxTestDao.selectByIdList(1L,p);
			System.out.println(JSONObject.toJSONString(list));
			
			p = new PageLimitBounds(2,2);
			MasterAndSlaveDataSourceHolder.putDataSourceMaster();
			list = jdxTestDao.selectByIdList(1L,p);
			System.out.println(JSONObject.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testJdxTestDao() {
		try {
			JdxTestEntity jdxTestEntity = jdxTestDao.selectById(1L);
			System.out.println(JSONObject.toJSONString(jdxTestEntity));
			
			MasterAndSlaveDataSourceHolder.putDataSourceMaster();
			jdxTestEntity = jdxTestDao.selectById(1L);
			System.out.println(JSONObject.toJSONString(jdxTestEntity));
			
			jdxTestEntity = jdxTestDao.selectById(1L);
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
		hellowordService.saveInRedis("jdx", list.get(0));
		System.out.println(JSONObject.toJSONString(hellowordService.getInRedis("jdx")));
	}

	@Test
	public void testRedisLock() throws Exception {
		for (int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				public void run() {
					hellowordService.redisLock("jdx");
				}
			}).start();
		}
		Thread.sleep(15000);
	}

}
