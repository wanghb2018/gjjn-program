package com.gjjn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gjjn.dao.MyjianniangMapper;
import com.gjjn.dao.entity.Myjianniang;

@Service
public class MyjianniangService {
	@Autowired
	MyjianniangMapper mapper;
	
	public void create(Myjianniang myjianniang) {
		mapper.insertSelective(myjianniang);
	}
	
	public List<Myjianniang> getByIdList(List<Integer> jnIds){
		return mapper.selectByPrimaryKeys(jnIds);
	}
	
	public List<Myjianniang> getRoleJns(Integer roleId){
		return mapper.getRoleJns(roleId);
	}
}
