package com.gjjn.dao;

import java.util.List;

import com.gjjn.dao.entity.Suipian;

public interface SuipianMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Suipian record);

    int insertSelective(Suipian record);

    Suipian selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Suipian record);

    int updateByPrimaryKey(Suipian record);
    
    Suipian search(Suipian record);
    
    List<Suipian> getRoleSps(Integer roleId);
}