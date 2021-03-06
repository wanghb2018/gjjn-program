package com.mochen.dao;

import java.util.List;

import com.mochen.model.KeyanSJ;

public interface KeyanSJMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeyanSJ record);

    int insertSelective(KeyanSJ record);

    KeyanSJ selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KeyanSJ record);

    int updateByPrimaryKey(KeyanSJ record);
    
    List<KeyanSJ> getAll();
}