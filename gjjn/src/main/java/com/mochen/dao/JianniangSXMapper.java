package com.mochen.dao;

import org.springframework.cache.annotation.Cacheable;

import com.mochen.model.JianniangSX;
import com.mochen.utils.Constant;

public interface JianniangSXMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JianniangSX record);

    int insertSelective(JianniangSX record);

    @Cacheable(value= Constant.CACHE_YEAR, key= "'jnsx_'+#p0")
    JianniangSX selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JianniangSX record);

    int updateByPrimaryKey(JianniangSX record);
}