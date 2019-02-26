package com.mochen.dao;

import com.mochen.model.Jianniang;
import com.mochen.model.JianniangWithBLOBs;

public interface JianniangMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JianniangWithBLOBs record);

    int insertSelective(JianniangWithBLOBs record);

    JianniangWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JianniangWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(JianniangWithBLOBs record);

    int updateByPrimaryKey(Jianniang record);
}