package com.jiangdaxian.helloword.service;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jiangdaxian.jdxtest.dao.JdxTestDao;
import com.jiangdaxian.jdxtest.elasticsearch.JdxTestElasticsearch;
import com.jiangdaxian.jdxtest.entity.JdxTestEntity;
import com.jiangdaxian.jdxtest.mongo.JdxTestMongo;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import com.jiangdaxian.mongodb.MongoBeanUtil;
import com.jiangdaxian.redis.RedisLock;
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

	@Autowired
	private RedisLock redisLock;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	private org.elasticsearch.client.Client client;

	public static final String ELA_INDEX_NAME = "ela_index_name_select";
	public static final String ELA_TYPE_NAME = "ela_type_name";

	public void selectElasticsearch() {
		System.out.println(client);
		System.out.println(elasticsearchTemplate);

		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(ELA_INDEX_NAME).setTypes(ELA_TYPE_NAME)
				.setSearchType(SearchType.DEFAULT);

		IndexQueryBuilder builder = new IndexQueryBuilder().withIndexName(ELA_INDEX_NAME).withType(ELA_TYPE_NAME);
		List<IndexQuery> indexQueries = newArrayList();
		
		JdxTestElasticsearch jdxTestElasticsearch = new JdxTestElasticsearch();
		jdxTestElasticsearch.setId(System.currentTimeMillis());
		jdxTestElasticsearch.setName(Long.toString(System.currentTimeMillis()));
		IndexQuery indexQuery = builder.withId(Long.toString(jdxTestElasticsearch.getId())).withObject(jdxTestElasticsearch).build();
		indexQueries.add(indexQuery);

		elasticsearchTemplate.bulkIndex(indexQueries);
		
		//select
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("id", "1529728565504"));
        searchRequestBuilder.setQuery(queryBuilder);
        SearchResponse searchResponse = searchRequestBuilder.get(TimeValue.timeValueSeconds(3));;
        SearchHit[] hits = searchResponse.getHits().getHits();
        if (hits != null && hits.length > 0) {
        	for(SearchHit searchHit:hits) {
	            String result = searchHit.getSourceAsString();
	            JdxTestElasticsearch resultList = JSONObject.parseObject(result, JdxTestElasticsearch.class);
	            if (resultList != null) {
	                System.out.println(JSONObject.toJSONString(result));
	            }
        	}
        }
	}

	public String sayHello() {
		String str = JSONObject.toJSONString(jdxTestDao.selectById(1L));
		LOG.info("str:{}", str);
		return "jdx hello world," + str;
	}

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<JdxTestMongo> getItemInfoInMongo(Long id) throws Exception {
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

	public int saveInMongo(JdxTestMongo itemInfo) throws Exception {
		DBCollection collection = this.mongoTemplate.getCollection(COLLECTION_NAME);
		int result = 0;
		DBObject iteminfoObj = MongoBeanUtil.bean2DBObject(itemInfo);
		WriteResult writeResult = collection.save(iteminfoObj);
		result = writeResult.getN();
		return result;
	}

	@Autowired
	private RedisTemplate redisTemplate;

	public void saveInRedis(String name, JdxTestMongo itemInfo) {
		redisTemplate.opsForValue().set(name, itemInfo);
	}

	public JdxTestMongo getInRedis(String name) {
		return (JdxTestMongo) redisTemplate.opsForValue().get(name);
	}

	public void redisLock(String s) {
		try {
			redisLock.lockByIncr(s);
			Thread.sleep(2000);
			System.out.println(new java.util.Date() + "," + Thread.currentThread().getName());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			redisLock.unlock(s);
		}
	}
}
