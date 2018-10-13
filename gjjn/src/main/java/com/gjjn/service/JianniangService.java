package com.gjjn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gjjn.dao.JianniangMapper;
import com.gjjn.dao.entity.Jianniang;

@Service
public class JianniangService {
	@Autowired
	JianniangMapper mapper;
	
	public Jianniang getById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}
}
