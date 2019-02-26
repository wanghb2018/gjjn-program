package com.mochen.dao;

import com.mochen.model.JianniangMaps;

public interface JianniangMapsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JianniangMaps record);

    int insertSelective(JianniangMaps record);

    JianniangMaps selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JianniangMaps record);

    int updateByPrimaryKey(JianniangMaps record);
}