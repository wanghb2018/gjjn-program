package com.gjjn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gjjn.dao.GameMapMapper;
import com.gjjn.dao.entity.GameMap;

@Service
public class GameMapService {
	@Autowired
	GameMapMapper mapper;
	
	public List<GameMap> getAll(){
		return mapper.getAll();
	}
	
}
