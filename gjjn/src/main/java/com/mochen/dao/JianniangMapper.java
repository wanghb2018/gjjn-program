package com.mochen.dao;

import org.springframework.cache.annotation.Cacheable;

import com.mochen.model.Jianniang;
import com.mochen.utils.Constant;

public interface JianniangMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Jianniang record);

    int insertSelective(Jianniang record);
    
    @Cacheable(value=Constant.CACHE_YEAR,key="'jianniang_'+#p0")
    Jianniang selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jianniang record);

    int updateByPrimaryKey(Jianniang record);
}