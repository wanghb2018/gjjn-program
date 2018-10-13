package com.gjjn.dao;

import java.util.List;

import com.gjjn.dao.entity.Rolesj;

public interface RolesjMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rolesj record);

    int insertSelective(Rolesj record);

    Rolesj selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rolesj record);

    int updateByPrimaryKey(Rolesj record);
    
    List<Rolesj> getAll();
}