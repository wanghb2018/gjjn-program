package com.mochen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mochen.dao.GameMapMapper;
import com.mochen.dao.JianniangMapsMapper;
import com.mochen.dao.KeyanSJMapper;
import com.mochen.model.GameMap;
import com.mochen.model.JianniangMaps;
import com.mochen.model.KeyanSJ;
import com.mochen.utils.Constant;

@Service
public class GameMapService {
	@Autowired
	GameMapMapper gameMapMapper;
	@Autowired
	JianniangMapsMapper jianniangMapsMapper;
	@Autowired
	KeyanSJMapper keyanSJMapper;
	
	@Cacheable(value = Constant.CACHE_YEAR, key = Constant.CACHE_ALL_KEYANSJ)
	public List<KeyanSJ> getAllKeyanSJ(){
		return keyanSJMapper.getAll();
	}
	
	@Cacheable(value = Constant.CACHE_YEAR, key = "'keyansj_'+#id")
	public KeyanSJ getKeyanSjById(Integer id) {
		return keyanSJMapper.selectByPrimaryKey(id);
	}

	@Cacheable(value = Constant.CACHE_YEAR, key = "'gameMap_'+#id")
	public GameMap getGameMapById(Integer id) {
		return gameMapMapper.selectByPrimaryKey(id);
	}
	
	@Cacheable(value = Constant.CACHE_YEAR, key = Constant.CACHE_ALL_GAMEMAP)
	public List<GameMap> getAllGameMap() {
		return gameMapMapper.getAll();
	}
	
	@Cacheable(value = Constant.CACHE_YEAR, key = "'jianniangmap_'+#mapId")
	public List<JianniangMaps> getByMapId(Integer mapId){
		return jianniangMapsMapper.getByMapId(mapId);
	}
}
