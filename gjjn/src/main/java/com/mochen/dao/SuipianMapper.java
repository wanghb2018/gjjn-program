package com.mochen.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mochen.model.Suipian;

public interface SuipianMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Suipian record);

    int insertSelective(Suipian record);

    Suipian selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Suipian record);

    int updateByPrimaryKey(Suipian record);
    
    List<Suipian> getAllUserSps(Integer roleId);
    
    void batchSave(List<Suipian> sps);
    
    List<Suipian> getUserSps(@Param("id")Integer id, @Param("roleId")Integer roleId);
    
    List<Suipian> getSpByJnId(@Param("jnId")Integer jnId, @Param("roleId")Integer roleId);
    
    void batchUpdate(List<Suipian> sps);
    
    Integer saleSuipianExist(Integer roleId);
    
    Integer saleSuipianFull(Integer roleId);
    
    List<Suipian> getRoleBl(Integer roleId);
}