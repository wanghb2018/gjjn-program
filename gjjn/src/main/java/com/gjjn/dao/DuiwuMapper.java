package com.gjjn.dao;

import com.gjjn.dao.entity.Duiwu;

public interface DuiwuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Duiwu record);

    int insertSelective(Duiwu record);

    Duiwu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Duiwu record);

    int updateByPrimaryKey(Duiwu record);
    
    Duiwu getByRoleId(Integer roleId);
}