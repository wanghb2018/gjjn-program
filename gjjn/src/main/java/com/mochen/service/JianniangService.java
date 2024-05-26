package com.mochen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mochen.dao.JianniangMapper;
import com.mochen.dao.JianniangSJMapper;
import com.mochen.dao.JianniangSXMapper;
import com.mochen.dao.SuipianMapper;
import com.mochen.model.Jianniang;
import com.mochen.model.JianniangSJ;
import com.mochen.model.JianniangSX;
import com.mochen.model.Suipian;
import com.mochen.utils.Constant;

@Service
public class JianniangService {
	@Autowired
	JianniangMapper jianniangMapper;
	@Autowired
	SuipianMapper suipianMapper;
	@Autowired
	JianniangSJMapper jianniangSJMapper;
	@Autowired
	JianniangSXMapper jianniangSXMapper;
	private volatile Map<String, Suipian> userSPMap = new ConcurrentHashMap<>();
	
	@Cacheable(value = Constant.CACHE_YEAR, key = "'jianniang_'+#id", unless = "#result == null")
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

	public void spBatchSave(List<Suipian> sps) {
		for (Suipian sp : sps) {
			userSPMap.compute(sp.getRoleId() + "_" + sp.getJnId(), (k, v) -> {
				if (v != null) {
					v.setNum(v.getNum() + sp.getNum());
					return v;
				} else {
					return sp;
				}
			});
		}
	}
	
	@Scheduled(cron = "*/10 * * * * *")
	@Async("myThreadPool")
	public void spSaveToDatabase() {
		List<Suipian> sps = null;
		synchronized (userSPMap) {
			sps = new ArrayList<>(userSPMap.values());
			userSPMap.clear();
		}
		if (!CollectionUtils.isEmpty(sps)) {
			suipianMapper.batchSave(sps);
		}
	}
	
	public void spBatchUpdate(List<Suipian> sps) {
		suipianMapper.batchUpdate(sps);
	}

	@Cacheable(value = Constant.CACHE_YEAR, key = "'jnsj_'+#id")
	public JianniangSJ getJnsjById(Integer id) {
		return jianniangSJMapper.selectByPrimaryKey(id);
	}
	
	public List<Suipian> getUserSpsById(Integer id, Integer roleId) {
		return suipianMapper.getUserSps(id, roleId);
	}
	
	public List<Suipian> getSpByJnId(Integer jnId, Integer roleId) {
		return suipianMapper.getSpByJnId(jnId, roleId);
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
	
	@Cacheable(value = Constant.CACHE_YEAR, key = Constant.CACHE_ALL_JIANNIANG)
	public List<Jianniang> getAllJn() {
		return jianniangMapper.getAll();
	}

	@Cacheable(value = Constant.CACHE_YEAR, key = Constant.CACHE_ALL_JIANNIANG_MAP)
	public Map<Integer, Jianniang> getAllJnMap() {
		return jianniangMapper.getAll().stream().collect(Collectors.toMap(Jianniang::getId, Function.identity()));
	}
	
	@Cacheable(value = Constant.CACHE_YEAR, key = "'all_jianniang_pinji_'+#pinji")
	public List<Jianniang> getAllJnByPinji(Integer pinji) {
		return jianniangMapper.getAllByPinji(pinji);
	}
	
	@Cacheable(value = Constant.CACHE_YEAR, key = "'all_jianniang_over_pinji_'+#pinji")
	public List<Jianniang> getAllJnByOverPinji(Integer pinji) {
		return jianniangMapper.getAllByOverPinji(pinji);
	}
	
	public List<Jianniang> getLoseJn(Integer roleId) {
		return jianniangMapper.getLoseJn(roleId);
	}
	
	public List<Suipian> getRoleBl(Integer roleId) {
		return suipianMapper.getRoleBl(roleId);
	}
	
	@Cacheable(value = Constant.CACHE_YEAR, key = "'jnsx_'+#id")
	public JianniangSX getJnSXbyId(Integer id) {
		return jianniangSXMapper.selectByPrimaryKey(id);
	}

}
