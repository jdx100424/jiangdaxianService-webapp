package com.jiangdaxian.helloword.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiangdaxian.jdxtest.dao.JdxTestDao;

@Service
public class HellowordService {
	@Autowired
	private JdxTestDao jdxTestDao;
	
	public String sayHello() {
		return "jdx hello world," + jdxTestDao.selectById(1L);
	}
}
