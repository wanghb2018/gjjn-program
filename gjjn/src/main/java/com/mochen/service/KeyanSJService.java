package com.mochen.service;

import com.mochen.dao.KeyanSJMapper;
import com.mochen.model.KeyanSJ;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyanSJService {
    @Autowired
    KeyanSJMapper keyanSJMapper;

    @Cacheable(value = Constant.CACHE_YEAR, key = Constant.CACHE_ALL_KEYANSJ)
    public List<KeyanSJ> getAll(){
        return keyanSJMapper.getAll();
    }

    @Cacheable(value = Constant.CACHE_YEAR, key = "'keyansj_'+#id")
    public KeyanSJ getById(Integer id) {
        return keyanSJMapper.selectByPrimaryKey(id);
    }
}
