package com.mochen.dao;

import org.springframework.cache.annotation.Cacheable;

import com.mochen.model.JianniangSJ;
import com.mochen.utils.Constant;

public interface JianniangSJMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(JianniangSJ record);

	int insertSelective(JianniangSJ record);

	@Cacheable(value = Constant.CACHE_YEAR, key = "'jnsj_'+#p0")
	JianniangSJ selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(JianniangSJ record);

	int updateByPrimaryKey(JianniangSJ record);
}