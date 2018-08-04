package com.jiangdaxian.jdxtest.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jiangdaxian.jdxtest.entity.JdxTestEntity;
import com.jiangdaxian.mybatis.pagelimit.PageLimitBounds;

@Repository
public interface JdxTestDao {
	public JdxTestEntity selectById(@Param("id")Long id);
	public List<JdxTestEntity> selectByIdList(@Param("id")Long id,PageLimitBounds pageLimitBounds);
}
