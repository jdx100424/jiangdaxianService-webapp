package com.jiangdaxian.jdxtest.elasticsearch;

import static org.springframework.data.elasticsearch.annotations.FieldIndex.analyzed;
import static org.springframework.data.elasticsearch.annotations.FieldType.String;
import static org.springframework.data.elasticsearch.annotations.FieldType.Long;

import java.io.Serializable;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "ela_index_name", type = "ela_type_name")
public class JdxTestElasticsearch implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Field(type = Long, index = analyzed)
	private Long id;
	
    @Field(type = String, index = analyzed)
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
