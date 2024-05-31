package com.mochen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mochen.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mochen.dao.DuiwuMapper;
import com.mochen.service.data.DuiwuInfo;
import com.mochen.utils.Constant;

@Service
public class DuiwuService {
	@Autowired
	DuiwuMapper duiwuMapper;

	@Autowired
	DuiwuService duiwuService;
	@Autowired
	MyJianniangService myJianniangService;
	@Autowired
	JianniangService jianniangService;
	@Autowired
	JunxianService junxianService;
	@Autowired
	KeyanService keyanService;
	@Autowired
	AccountService accountService;

	public void create(Duiwu duiwu) {
		duiwuMapper.insertSelective(duiwu);
	}
	
	public Duiwu getDuiwuByRoleId(Integer roleId) {
		return duiwuMapper.getByRoleId(roleId);
	}

	public Duiwu updateDuiwu(Duiwu duiwu) {
		duiwuMapper.updateByPrimaryKey(duiwu);
		return duiwu;
	}

	@Cacheable(value = Constant.CACHE_YEAR, key = "'duiwuinfo_'+#roleId")
	public DuiwuInfo getDuiwuInfo(Integer roleId) {
		Duiwu duiwu = getDuiwuByRoleId(roleId);
		List<Integer> myJnIds = duiwuToMyJnIds(duiwu);
		if (CollectionUtils.isEmpty(myJnIds)) {
			return new DuiwuInfo(duiwu);
		}
		List<MyJianniang> myJns = myJianniangService.getByIds(myJnIds);
        return new DuiwuInfo(duiwu, myJns);
	}

	@CachePut(value = Constant.CACHE_YEAR, key = "'duiwuinfo_'+#roleId")
	public DuiwuInfo reCalDwZdl(Integer roleId) {
		Duiwu duiwu = getDuiwuByRoleId(roleId);
		List<Integer> myJnIds = duiwuToMyJnIds(duiwu);
		if (CollectionUtils.isEmpty(myJnIds)) {
			return new DuiwuInfo(duiwu);
		}
		List<MyJianniang> myJns = myJianniangService.getByIds(myJnIds);
		if (CollectionUtils.isEmpty(myJns)) {
			DuiwuInfo duiwuInfo = new DuiwuInfo(duiwu);
			updateDuiwu(duiwuInfo.toDuiwu());
			return new DuiwuInfo(duiwu);
		}
		Keyan keyan = keyanService.getByRoleId(roleId);
		Role role = accountService.getRoleById(roleId);
		Junxian junxian = junxianService.getById(role.getJunxianId());
		double jxRate = (double)junxian.getPowerrate() / 100 + 1;
		int totalZdl = 0;
		for (MyJianniang myJn : myJns) {
			myJn.calShuxing(keyan);
			totalZdl = totalZdl + myJn.getZdl();
		}
		myJianniangService.batchUpdate(myJns);
		DuiwuInfo response = new DuiwuInfo(duiwu, myJns, jxRate);
		duiwu.setCount(myJns.size());
		duiwu.setTotalzdl(response.getZdl());
		updateDuiwu(duiwu);
		return response;
	}

	@CacheEvict(value = Constant.CACHE_YEAR, key = "'duiwuinfo_'+#roleId")
	public Integer shangzhen(Integer roleId, Integer myJnId) {
		DuiwuInfo duiwuInfo = duiwuService.getDuiwuInfo(roleId);
		if (duiwuInfo.getMyJns().size() >= 6) {
			return Constant.FAILED;
		}
		if (duiwuInfo.getMyJns().stream().anyMatch(e -> e.getId().equals(myJnId))) {
			return Constant.OTHER;
		}

		MyJianniang myJn = myJianniangService.getJnById(myJnId);
		myJn.setIswar(1);

		Keyan keyan = keyanService.getByRoleId(roleId);
		myJn.calShuxing(keyan);
		duiwuInfo.getMyJns().add(myJn);

		Role role = accountService.getRoleById(roleId);
		Junxian junxian = junxianService.getById(role.getJunxianId());
		double jxRate = (double)junxian.getPowerrate() / 100 + 1;
		duiwuInfo.calDwZdl(jxRate);

		Duiwu duiwu = duiwuInfo.toDuiwu();
		updateDuiwu(duiwu);

		myJianniangService.update(myJn);
		return Constant.SUCCESS;
	}

	@CacheEvict(value = Constant.CACHE_YEAR, key = "'duiwuinfo_'+#roleId")
	public Integer xiuxi(Integer roleId, Integer myJnId) {
		DuiwuInfo duiwuInfo = duiwuService.getDuiwuInfo(roleId);
		duiwuInfo.getMyJns().removeIf(e -> e.getId().equals(myJnId));
		Role role = accountService.getRoleById(roleId);
		Junxian junxian = junxianService.getById(role.getJunxianId());
		double jxRate = (double)junxian.getPowerrate() / 100 + 1;
		duiwuInfo.calDwZdl(jxRate);

		MyJianniang myJn = myJianniangService.getJnById(myJnId);
		myJn.setIswar(0);
		myJn.calShuxing(null);

		updateDuiwu(duiwuInfo.toDuiwu());
		myJianniangService.update(myJn);
		return Constant.SUCCESS;
	}

	private List<Integer> duiwuToMyJnIds(Duiwu duiwu) {
		return Stream.of(duiwu.getOneId(), duiwu.getTwoId(), duiwu.getThreeId(), duiwu.getFourId(),
				duiwu.getFiveId(), duiwu.getSixId()).filter(Objects::nonNull).collect(Collectors.toList());
	}
	
	public void duiwuAddJy(Integer roleId, int jy) {
		myJianniangService.jnAddjy(roleId, jy);
	}


}
