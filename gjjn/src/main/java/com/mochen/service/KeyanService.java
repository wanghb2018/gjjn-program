package com.mochen.service;

import com.mochen.dao.KeyanMapper;
import com.mochen.model.Keyan;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class KeyanService {
    @Autowired
    KeyanMapper keyanMapper;

    @Cacheable(value = Constant.CACHE_YEAR, key = "'keyan_'+#roleId")
    public Keyan getByRoleId(Integer roleId) {
        return keyanMapper.getByRoleId(roleId);
    }

    @CacheEvict(value = Constant.CACHE_YEAR, key = "'keyan_'+#keyan.roleId")
    public void updateKeyan(Keyan keyan) {
        keyanMapper.updateByPrimaryKey(keyan);
    }
}
