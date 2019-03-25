package com.mochen.dao;

import com.mochen.model.Duiwu;

public interface DuiwuMapper {
    int insertSelective(Duiwu record);

    int updateByPrimaryKey(Duiwu record);
    
    Duiwu getByRoleId(Integer roleId);
}