package com.mochen.service;

import com.mochen.dao.JunxianMapper;
import com.mochen.model.Junxian;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JunxianService {
    @Autowired
    JunxianMapper junxianMapper;

    @Cacheable(value = Constant.CACHE_YEAR, key = Constant.CACHE_ALL_JUNXIAN)
    public List<Junxian> getAllJunxian() {
        return junxianMapper.getAll();
    }

    @Cacheable(value = Constant.CACHE_YEAR, key = "'junxian_'+#p0")
    public Junxian getById(Integer id) {
        return junxianMapper.selectByPrimaryKey(id);
    }
}
