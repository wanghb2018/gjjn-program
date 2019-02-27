package com.mochen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mochen.dao.GameMapMapper;
import com.mochen.model.GameMap;
import com.mochen.utils.Constant;

@Service
public class GameMapService {
	@Autowired
	GameMapMapper gameMapMapper;

	@Cacheable(value = Constant.CACHE_YEAR, key = "'gameMap_'+#id")
	public GameMap getGameMapById(Integer id) {
		return gameMapMapper.selectByPrimaryKey(id);
	}

	@Cacheable(value = Constant.CACHE_YEAR, key = Constant.CACHE_ALL_GAMEMAP)
	public List<GameMap> getAllGameMap() {
		return gameMapMapper.getAll();
	}
}