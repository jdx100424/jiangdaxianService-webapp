package com.jiangdaxian.jdxtest.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jiangdaxian.jdxtest.entity.JdxTestEntity;

@Repository
public interface JdxTestDao {
	public JdxTestEntity selectById(@Param("id")Long id);
}
