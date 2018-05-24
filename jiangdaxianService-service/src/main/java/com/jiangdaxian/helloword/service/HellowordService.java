package com.jiangdaxian.helloword.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jiangdaxian.jdxtest.dao.JdxTestDao;

@Service
public class HellowordService {
	private static final Logger LOG = LoggerFactory.getLogger(HellowordService.class);

	@Autowired
	private JdxTestDao jdxTestDao;
	
	public String sayHello() {
		String str = JSONObject.toJSONString(jdxTestDao.selectById(1L)) ;
		LOG.info("str:{}",str);
		return "jdx hello world," +str;
	}
}
