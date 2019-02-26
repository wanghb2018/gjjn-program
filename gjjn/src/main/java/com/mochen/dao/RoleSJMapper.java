package com.mochen.dao;

import com.mochen.model.RoleSJ;

public interface RoleSJMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleSJ record);

    int insertSelective(RoleSJ record);

    RoleSJ selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleSJ record);

    int updateByPrimaryKey(RoleSJ record);
}