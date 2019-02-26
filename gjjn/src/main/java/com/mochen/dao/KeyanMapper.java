package com.mochen.dao;

import com.mochen.model.Keyan;

public interface KeyanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Keyan record);

    int insertSelective(Keyan record);

    Keyan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Keyan record);

    int updateByPrimaryKey(Keyan record);
}