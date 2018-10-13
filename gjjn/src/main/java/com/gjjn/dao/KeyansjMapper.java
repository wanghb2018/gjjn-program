package com.gjjn.dao;

import com.gjjn.dao.entity.Keyansj;

public interface KeyansjMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Keyansj record);

    int insertSelective(Keyansj record);

    Keyansj selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Keyansj record);

    int updateByPrimaryKey(Keyansj record);
}