package com.jiangdaxian.test.elasticsearch.mapping;

import java.io.Serializable;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import com.jiangdaxian.jdxtest.elasticsearch.JdxTestElasticsearch;
import com.jiangdaxian.test.BaseTestCase;

public class TestMapping extends BaseTestCase {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Test
	public void testPutJdxTestElasticsearchMapping() {
		elasticsearchTemplate.putMapping(JdxTestElasticsearch.class);
	}

}
