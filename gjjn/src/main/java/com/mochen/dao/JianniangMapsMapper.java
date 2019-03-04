package com.mochen.dao;

import java.util.List;

import com.mochen.model.JianniangMaps;

public interface JianniangMapsMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(JianniangMaps record);

	int insertSelective(JianniangMaps record);

	JianniangMaps selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(JianniangMaps record);

	int updateByPrimaryKey(JianniangMaps record);

	List<JianniangMaps> getByMapId(Integer mapId);
}