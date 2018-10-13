package com.gjjn.dao;

import com.gjjn.dao.entity.Jnsx;

public interface JnsxMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Jnsx record);

    int insertSelective(Jnsx record);

    Jnsx selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jnsx record);

    int updateByPrimaryKey(Jnsx record);
}