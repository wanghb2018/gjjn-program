package com.mochen.dao;

import java.util.List;

import com.mochen.model.Suipian;

public interface SuipianMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Suipian record);

    int insertSelective(Suipian record);

    Suipian selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Suipian record);

    int updateByPrimaryKey(Suipian record);
    
    List<Suipian> getAllUserSps(Integer roleId);
    
    void batchSave(List<Suipian> sps);
}