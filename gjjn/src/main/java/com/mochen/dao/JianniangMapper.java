package com.mochen.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.mochen.model.Jianniang;
import com.mochen.utils.Constant;

public interface JianniangMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Jianniang record);

    int insertSelective(Jianniang record);
    
    @Cacheable(value=Constant.CACHE_YEAR,key="'jianniang_'+#p0", unless="#result == null")
    Jianniang selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jianniang record);

    int updateByPrimaryKey(Jianniang record);
    
    @Cacheable(value=Constant.CACHE_YEAR,key=Constant.CACHE_ALL_JIANNIANG)
    List<Jianniang> getAll();
    
    @Cacheable(value=Constant.CACHE_YEAR,key="'all_jianniang_pinji_'+#p0")
    List<Jianniang> getAllByPinji(Integer pinji);
    
    @Cacheable(value=Constant.CACHE_YEAR,key="'all_jianniang_over_pinji_'+#p0")
    List<Jianniang> getAllByOverPinji(Integer pinji);
    
    List<Jianniang> getLoseJn(Integer roleId);
}