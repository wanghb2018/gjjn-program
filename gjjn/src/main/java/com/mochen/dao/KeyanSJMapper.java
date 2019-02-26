package com.mochen.dao;

import com.mochen.model.KeyanSJ;

public interface KeyanSJMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KeyanSJ record);

    int insertSelective(KeyanSJ record);

    KeyanSJ selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KeyanSJ record);

    int updateByPrimaryKey(KeyanSJ record);
}