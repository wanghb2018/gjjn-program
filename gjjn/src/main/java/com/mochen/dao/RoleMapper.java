package com.mochen.dao;

import org.apache.ibatis.annotations.Param;

import com.mochen.model.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKeyWithBLOBs(Role record);

    int updateByPrimaryKey(Role record);
    
    Role getByUserId(Integer userId);
    
    void mapBossUpdate(@Param("id")Integer id,@Param("level")Integer level,@Param("exp")Integer exp,@Param("count")Integer count,@Param("wz")Integer wz,@Param("isOpen")Boolean isOpen);
}