package com.mochen.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import com.mochen.model.PhbInfo;
import com.mochen.model.Role;
import com.mochen.utils.Constant;

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
    
    void jianzaoUpdate(@Param("id")Integer id, @Param("wuzi")Integer wuzi, @Param("mofang")Integer mofang);
    
    @Cacheable(value=Constant.CACHE_HOUR, key="'dj_phb'")
    List<PhbInfo> djPhb();
    
    @Cacheable(value=Constant.CACHE_HOUR, key="'zdl_phb'")
    List<PhbInfo> zdlPhb();
    
    @Cacheable(value=Constant.CACHE_HOUR, key="'tj_phb'")
    List<PhbInfo> tjPhb();
    
    @Cacheable(value=Constant.CACHE_HOUR, key="'jn_phb'")
    List<PhbInfo> jnPhb();
    
    void updateRoleByChangeDetail(@Param("id")Integer id, @Param("zuanshiChange")Integer zuanshi, @Param("shiyouChange")Integer shiyou, @Param("mofangChange")Integer mofang, @Param("wuziChange")Integer wuzi, @Param("keyandianChange")Integer keyandian);
}