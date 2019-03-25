package com.mochen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mochen.dao.WorkMapper;
import com.mochen.model.Work;

@Service
public class WorkService {
	@Autowired
	WorkMapper mapper;
	
	public Work getById(Integer id) {
		return mapper.getById(id);
	}
	
	public List<Work> getByStatus(Integer status) {
		return mapper.getByStatus(status);
	}
	
	public void create(Work work) {
		mapper.insertSelective(work);
	}
	
	public void update(Work work) {
		mapper.updateByPrimaryKeySelective(work);
	}
}
