package com.gjjn.dao;

import com.gjjn.dao.entity.Junxian;

public interface JunxianMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Junxian record);

    int insertSelective(Junxian record);

    Junxian selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Junxian record);

    int updateByPrimaryKey(Junxian record);
}