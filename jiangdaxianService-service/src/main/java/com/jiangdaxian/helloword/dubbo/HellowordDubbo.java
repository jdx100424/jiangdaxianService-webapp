package com.jiangdaxian.helloword.dubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiangdaxian.helloword.service.HellowordService;

@Service("hellowordDubbo")
public class HellowordDubbo implements com.jiangdaxian.helloword.api.HellowordApi{

	@Autowired
	private HellowordService hellowordService;
	
	public String sayHello() {
		return hellowordService.sayHello();
	}

}
