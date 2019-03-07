package com.mochen.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.mochen.model.Junxian;
import com.mochen.utils.Constant;

public interface JunxianMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Junxian record);

	int insertSelective(Junxian record);

	@Cacheable(value = Constant.CACHE_YEAR, key = "'junxian_'+#p0")
	Junxian selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Junxian record);

	int updateByPrimaryKey(Junxian record);

	@Cacheable(value = Constant.CACHE_YEAR, key = "'junxian_all'")
	public List<Junxian> getAll();
}