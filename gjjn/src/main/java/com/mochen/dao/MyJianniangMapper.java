package com.mochen.dao;

import java.util.List;

import com.mochen.model.MyJianniang;

public interface MyJianniangMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MyJianniang record);

    int insertSelective(MyJianniang record);

    MyJianniang selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MyJianniang record);

    int updateByPrimaryKey(MyJianniang record);
    
    List<MyJianniang> getByIdList(List<Integer> ids);
    
    void batchUpdate(List<MyJianniang> myJns);
    
    List<MyJianniang> getUserJns(Integer roleId);
}