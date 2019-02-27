package com.mochen.dao;

import org.springframework.cache.annotation.Cacheable;

import com.mochen.model.Jianniang;
import com.mochen.model.JianniangWithBLOBs;
import com.mochen.utils.Constant;

public interface JianniangMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JianniangWithBLOBs record);

    int insertSelective(JianniangWithBLOBs record);
    
    @Cacheable(value = Constant.CACHE_YEAR, key = "'jianniang_'+#p0")
    JianniangWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JianniangWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(JianniangWithBLOBs record);

    int updateByPrimaryKey(Jianniang record);
}