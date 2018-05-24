package com.jiangdaxian.helloword.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jiangdaxian.jdxtest.dao.JdxTestDao;
import com.jiangdaxian.jdxtest.entity.JdxTestEntity;
import com.jiangdaxian.jdxtest.mongo.JdxTestMongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import com.jiangdaxian.mongodb.MongoBeanUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;


@Service
public class HellowordService {
	private static final Logger LOG = LoggerFactory.getLogger(HellowordService.class);
	private final static String COLLECTION_NAME = "jdxTest";

	@Autowired
	private JdxTestDao jdxTestDao;

	public String sayHello() {
		String str = JSONObject.toJSONString(jdxTestDao.selectById(1L));
		LOG.info("str:{}", str);
		return "jdx hello world," + str;
	}

	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<JdxTestMongo> getItemInfo(Long id) throws Exception {
		List<JdxTestMongo> list = new ArrayList<JdxTestMongo>();
		// 判断查询的json中传递过来的参数
		DBObject query = new BasicDBObject();
		query.put("id", id);
		DBCursor results = mongoTemplate.getCollection(COLLECTION_NAME).find(query);
		if (null != results) {
			Iterator<DBObject> iterator = results.iterator();
			while (iterator.hasNext()) {
				BasicDBObject obj = (BasicDBObject) iterator.next();
				JdxTestMongo itemInfo = new JdxTestMongo();
				itemInfo = MongoBeanUtil.dbObject2Bean(obj, itemInfo);
				list.add(itemInfo);
			}
		}
		return list;
	}

	public int save(JdxTestMongo itemInfo) throws Exception {
		DBCollection collection = this.mongoTemplate.getCollection(COLLECTION_NAME);
		int result = 0;
		DBObject iteminfoObj = MongoBeanUtil.bean2DBObject(itemInfo);
		WriteResult writeResult = collection.save(iteminfoObj);
		result = writeResult.getN();
		return result;
	}
	

}
