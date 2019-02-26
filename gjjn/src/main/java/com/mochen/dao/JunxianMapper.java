package com.mochen.dao;

import com.mochen.model.Junxian;

public interface JunxianMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Junxian record);

    int insertSelective(Junxian record);

    Junxian selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Junxian record);

    int updateByPrimaryKey(Junxian record);
}