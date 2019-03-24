package com.mochen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.mochen.dao.JianniangMapper;
import com.mochen.dao.JianniangSJMapper;
import com.mochen.dao.JianniangSXMapper;
import com.mochen.dao.MyJianniangMapper;
import com.mochen.dao.SuipianMapper;
import com.mochen.model.Jianniang;
import com.mochen.model.JianniangSJ;
import com.mochen.model.JianniangSX;
import com.mochen.model.MyJianniang;
import com.mochen.model.Suipian;
import com.mochen.utils.Constant;

@Service
public class JianniangService {
	@Autowired
	JianniangMapper jianniangMapper;
	@Autowired
	MyJianniangMapper myJianniangMapper;
	@Autowired
	SuipianMapper suipianMapper;
	@Autowired
	JianniangSJMapper jianniangSJMapper;
	@Autowired
	JianniangSXMapper jianniangSXMapper;

	public Jianniang getById(Integer id) {
		return jianniangMapper.selectByPrimaryKey(id);
	}
	
	@Caching(put = { @CachePut(value = Constant.CACHE_YEAR, key = "'jianniang_'+#p0.id") }, evict = { @CacheEvict(value = Constant.CACHE_YEAR, key = Constant.CACHE_ALL_JIANNIANG),
			@CacheEvict(value = Constant.CACHE_YEAR, key = "'all_jianniang_pinji_'+#p0.pinji"), @CacheEvict(value = Constant.CACHE_YEAR, key = "'all_jianniang_over_pinji_2'"),
			@CacheEvict(value = Constant.CACHE_YEAR, key = "'all_jianniang_over_pinji_5'") })
	public Jianniang addNewJn(Jianniang jn) {
		jianniangMapper.insert(jn);
		return jn;
	}
	
	public MyJianniang addMyJN(Integer roleId, Jianniang jn, Integer isWar) {
		MyJianniang myJN = new MyJianniang(roleId, jn, isWar);
		myJN.calJNZdl(0);
		myJianniangMapper.insert(myJN);
		return myJN;
	}

	public void addMyJN(Integer roleId, Jianniang jn) {
		addMyJN(roleId, jn, 0);
	}

	public List<MyJianniang> getUserJns(Integer roleId) {
		return myJianniangMapper.getUserJns(roleId);
	}

	public List<Suipian> getUserSps(Integer roleId) {
		return suipianMapper.getAllUserSps(roleId);
	}

	public void spBatchSave(List<Suipian> sps) {
		suipianMapper.batchSave(sps);
	}

	public void spBatchUpdate(List<Suipian> sps) {
		suipianMapper.batchUpdate(sps);
	}

	public MyJianniang getMyJnById(Integer id) {
		return myJianniangMapper.selectByPrimaryKey(id);
	}

	public JianniangSJ getJnsjById(Integer id) {
		return jianniangSJMapper.selectByPrimaryKey(id);
	}

	public List<Suipian> getUserSpsById(Integer id, Integer roleId) {
		return suipianMapper.getUserSps(id, roleId);
	}
	
	public List<Suipian> getSpByJnId(Integer jnId, Integer roleId) {
		return suipianMapper.getSpByJnId(jnId, roleId);
	}

	public MyJianniang getByJnId(Integer roleId, Integer jnId) {
		return myJianniangMapper.getByJnId(roleId, jnId);
	}

	public Suipian getSpById(Integer id) {
		return suipianMapper.selectByPrimaryKey(id);
	}

	public Integer saleSuipianExist(Integer roleId) {
		return suipianMapper.saleSuipianExist(roleId);
	}

	public Integer saleSuipianFull(Integer roleId) {
		return suipianMapper.saleSuipianFull(roleId);
	}

	public List<Jianniang> getAllJn() {
		return jianniangMapper.getAll();
	}
	
	public List<Jianniang> getAllJnByPinji(Integer pinji){
		return jianniangMapper.getAllByPinji(pinji);
	}
	
	public List<Jianniang> getAllJnByOverPinji(Integer pinji){
		return jianniangMapper.getAllByOverPinji(pinji);
	}
	
	public List<Jianniang> getLoseJn(Integer roleId){
		return jianniangMapper.getLoseJn(roleId);
	}
	
	public List<Suipian> getRoleBl(Integer roleId) {
		return suipianMapper.getRoleBl(roleId);
	}
	
	public JianniangSX getJnSXbyId(Integer id) {
		return jianniangSXMapper.selectByPrimaryKey(id);
	}
	
	public void updateMyJn(MyJianniang myJn) {
		myJianniangMapper.updateByPrimaryKey(myJn);
	}
}
