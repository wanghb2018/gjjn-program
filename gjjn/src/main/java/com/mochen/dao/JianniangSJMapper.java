package com.mochen.dao;

import com.mochen.model.JianniangSJ;

public interface JianniangSJMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JianniangSJ record);

    int insertSelective(JianniangSJ record);

    JianniangSJ selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JianniangSJ record);

    int updateByPrimaryKey(JianniangSJ record);
}