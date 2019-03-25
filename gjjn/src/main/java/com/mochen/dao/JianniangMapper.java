package com.mochen.dao;

import java.util.List;

import com.mochen.model.Jianniang;

public interface JianniangMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Jianniang record);

    int insertSelective(Jianniang record);
    
    Jianniang selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Jianniang record);

    int updateByPrimaryKey(Jianniang record);
    
    List<Jianniang> getAll();
    
    List<Jianniang> getAllByPinji(Integer pinji);
    
    List<Jianniang> getAllByOverPinji(Integer pinji);
    
    List<Jianniang> getLoseJn(Integer roleId);
}