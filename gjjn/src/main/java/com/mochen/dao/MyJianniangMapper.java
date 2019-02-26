package com.mochen.dao;

import com.mochen.model.MyJianniang;

public interface MyJianniangMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MyJianniang record);

    int insertSelective(MyJianniang record);

    MyJianniang selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MyJianniang record);

    int updateByPrimaryKey(MyJianniang record);
}