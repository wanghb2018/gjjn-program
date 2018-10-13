package com.gjjn.dao;

import com.gjjn.dao.entity.Jnsj;

public interface JnsjMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Jnsj record);

    int insertSelective(Jnsj record);

    Jnsj selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jnsj record);

    int updateByPrimaryKey(Jnsj record);
}