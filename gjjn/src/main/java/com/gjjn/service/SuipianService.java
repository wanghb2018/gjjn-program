package com.gjjn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gjjn.dao.SuipianMapper;
import com.gjjn.dao.entity.Suipian;

@Service
public class SuipianService {
	@Autowired
	SuipianMapper mapper;
	
	public void insertOrUpdate(Suipian suipian) {
		Suipian result = mapper.search(suipian);
		if (result == null) {
			mapper.insertSelective(suipian);
		} else {
			result.setNum(result.getNum() + suipian.getNum());
			mapper.updateByPrimaryKey(suipian);
		}
	}
	
	public List<Suipian> getRoleSps(Integer roleId) {
		return mapper.getRoleSps(roleId);
	}
}
