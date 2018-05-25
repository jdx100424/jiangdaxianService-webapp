package com.jiangdaxian.helloword.kakfa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jiangdaxian.helloword.service.HellowordService;
import com.jiangdaxian.kafka.BaseConsumer;
import com.jiangdaxian.kafka.dto.MessageDto;

public class HellowordConsumer extends BaseConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(HellowordService.class);

	public HellowordConsumer(String topicName, String groupId,String kafkaIp, String kafkaPort, String kafkaServer) {
		super(topicName, groupId , kafkaIp, kafkaPort, kafkaServer);
	}

	@Override
	public void onMessage(MessageDto dto) {
		LOG.info("kafka receive:" + JSONObject.toJSONString(dto.getMessageInfo()));
	}
}
