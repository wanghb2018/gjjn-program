package com.mochen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mochen.dao.GameMapMapper;
import com.mochen.model.GameMap;

@Service
public class GameMapService {
	@Autowired
	GameMapMapper gameMapMapper;

	public GameMap getGameMapById(Integer id) {
		return gameMapMapper.selectByPrimaryKey(id);
	}

	public List<GameMap> getAllGameMap() {
		return gameMapMapper.getAll();
	}
}
