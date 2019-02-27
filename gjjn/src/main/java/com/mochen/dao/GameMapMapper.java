package com.mochen.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.mochen.model.GameMap;
import com.mochen.utils.Constant;

public interface GameMapMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GameMap record);

    int insertSelective(GameMap record);

    @Cacheable(value = Constant.CACHE_YEAR, key = "'gameMap_'+#p0")
    GameMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GameMap record);

    int updateByPrimaryKey(GameMap record);
    
    @Cacheable(value = Constant.CACHE_YEAR, key = Constant.CACHE_ALL_GAMEMAP)
    List<GameMap> getAll();
}