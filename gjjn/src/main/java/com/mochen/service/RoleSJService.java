package com.mochen.service;

import com.mochen.dao.RoleSJMapper;
import com.mochen.model.RoleSJ;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleSJService {
    @Autowired
    RoleSJMapper roleSJMapper;

    @Cacheable(value= Constant.CACHE_YEAR, key=Constant.CACHE_ALL_ROLESJ)
    public List<RoleSJ> getAll() {
        return roleSJMapper.getAll();
    }

    @Cacheable(value=Constant.CACHE_YEAR, key="'rolesj_'+#p0")
    public RoleSJ getById(Integer id) {
        return roleSJMapper.selectByPrimaryKey(id);
    }
}
