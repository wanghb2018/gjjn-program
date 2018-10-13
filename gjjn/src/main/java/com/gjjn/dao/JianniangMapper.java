package com.gjjn.dao;

import com.gjjn.dao.entity.Jianniang;

public interface JianniangMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Jianniang record);

    int insertSelective(Jianniang record);

    Jianniang selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jianniang record);

    int updateByPrimaryKey(Jianniang record);
}