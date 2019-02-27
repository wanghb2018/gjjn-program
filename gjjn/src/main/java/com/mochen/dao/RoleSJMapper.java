package com.mochen.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.mochen.model.RoleSJ;
import com.mochen.utils.Constant;

public interface RoleSJMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleSJ record);

    int insertSelective(RoleSJ record);

    @Cacheable(value=Constant.CACHE_YEAR, key="'rolesj_'+#p0")
    RoleSJ selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleSJ record);

    int updateByPrimaryKey(RoleSJ record);
    
    @Cacheable(value=Constant.CACHE_YEAR, key=Constant.CACHE_ALL_ROLESJ)
    List<RoleSJ> getAll();
}