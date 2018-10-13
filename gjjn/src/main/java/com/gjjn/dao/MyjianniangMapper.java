package com.gjjn.dao;

import java.util.List;

import com.gjjn.dao.entity.Myjianniang;

public interface MyjianniangMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Myjianniang record);

    int insertSelective(Myjianniang record);

    Myjianniang selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Myjianniang record);

    int updateByPrimaryKey(Myjianniang record);
    
    List<Myjianniang> selectByPrimaryKeys(List<Integer> jnIds);
    
    List<Myjianniang> getRoleJns(Integer roleId);
}