package com.jiangdaxian.jdxtest.mongo;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "jdxTest")
public class JdxTestMongo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Field(value = "id")
	private Long id;
	@Field(value = "name")
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
