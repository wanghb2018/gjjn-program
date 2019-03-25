package com.mochen.dao;

import com.mochen.model.JianniangSX;

public interface JianniangSXMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JianniangSX record);

    int insertSelective(JianniangSX record);

    JianniangSX selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JianniangSX record);

    int updateByPrimaryKey(JianniangSX record);
}