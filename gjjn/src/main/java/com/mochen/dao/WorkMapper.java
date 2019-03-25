package com.mochen.dao;

import java.util.List;

import com.mochen.model.Work;

public interface WorkMapper {
	Work getById(Integer id);
	
	List<Work> getByStatus(Integer status);
	
	void insertSelective(Work work);
	
	void updateByPrimaryKeySelective(Work work);
}
