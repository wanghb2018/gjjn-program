package com.mochen.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mochen.model.MyJianniang;

public interface MyJianniangMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MyJianniang record);

    int insertSelective(MyJianniang record);

    MyJianniang selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MyJianniang record);

    int updateByPrimaryKey(MyJianniang record);
    
    List<MyJianniang> getByIdList(List<Integer> ids);
    
    void batchSave(@Param("myJns")List<MyJianniang> myJns);
}