package com.mochen.dao;

import java.util.List;

import com.mochen.model.GameMap;

public interface GameMapMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GameMap record);

    int insertSelective(GameMap record);

    GameMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GameMap record);

    int updateByPrimaryKey(GameMap record);
    
    List<GameMap> getAll();
}