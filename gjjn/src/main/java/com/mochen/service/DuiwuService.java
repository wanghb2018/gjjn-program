package com.mochen.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mochen.dao.DuiwuMapper;
import com.mochen.dao.KeyanMapper;
import com.mochen.model.Duiwu;
import com.mochen.model.Jianniang;
import com.mochen.model.Junxian;
import com.mochen.model.Keyan;
import com.mochen.model.MyJianniang;
import com.mochen.model.Role;
import com.mochen.service.data.DuiwuData;
import com.mochen.utils.Constant;

@Service
public class DuiwuService {
	@Autowired
	MyJianniangService myJianniangService;
	@Autowired
	JianniangService jianniangService;
	@Autowired
	JunxianService junxianService;
	@Autowired
	KeyanMapper keyanMapper;
	@Autowired
	AccountService accountService;
	@Autowired
	DuiwuMapper duiwuMapper;

	public void create(Duiwu duiwu) {
		duiwuMapper.insertSelective(duiwu);
	}
	
	public Duiwu getDuiwuByRoleId(Integer roleId) {
		return accountService.getDuiwuByRoleId(roleId);
	}

	public DuiwuData reCalJNZdl(Role role) {
		Duiwu duiwu = accountService.getDuiwuByRoleId(role.getId());
		List<Integer> myJnIds = duiwuToMyJnIds(duiwu);
		if (CollectionUtils.isEmpty(myJnIds)) {
			return new DuiwuData();
		}
		List<MyJianniang> myJns = myJianniangService.getByIds(myJnIds);
		List<Jianniang> jns = new ArrayList<>();
		for (MyJianniang myJn : myJns) {
			jns.add(myJn.getJn());
		}
		Keyan keyan = keyanMapper.getByRoleId(role.getId());
		Junxian junxian = junxianService.getById(role.getJunxianId());
		double jxRate = (double)junxian.getPowerrate() / 100 + 1;
		for (int i = 0; i< myJns.size();i++) {
			myJns.get(i).calShuxing(jns.get(i), keyan, jxRate);
		}
		myJianniangService.batchUpdate(myJns);
		DuiwuData data = new DuiwuData(myJns);
		duiwu.setCount(myJns.size());
		duiwu.setTotalzdl(data.getZdl());
		accountService.updateDuiwu(duiwu);
		return data;

	}

	private List<Integer> duiwuToMyJnIds(Duiwu duiwu) {
		return Arrays.asList(duiwu.getOneId(), duiwu.getTwoId(), duiwu.getThreeId(), duiwu.getFourId(),
				duiwu.getFiveId(), duiwu.getSixId()).stream().filter(Objects::nonNull).collect(Collectors.toList());
	}
	
	public void duiwuAddJy(Integer roleId, int jy) {
		myJianniangService.jnAddjy(roleId, jy);
	}
	
	public Integer shangzhen(Integer roleId, Integer myJnId) {
		Duiwu duiwu = accountService.getDuiwuByRoleId(roleId);
		if (duiwu.getCount() == 6) {
			return Constant.FAILED;
		}
		List<Integer> myJnIds = duiwuToMyJnIds(duiwu);
		if (myJnIds.contains(myJnId)) {
			return Constant.OTHER;
		}
		myJnIds.add(myJnId);
		jnListToDuiwu(myJnIds, duiwu);
		accountService.updateDuiwu(duiwu);
		myJianniangService.jnSetWar(myJnId, 1);
		return Constant.SUCCESS;
	}
	
	public Integer xiuxi(Integer roleId, Integer myJnId) {
		Duiwu duiwu = accountService.getDuiwuByRoleId(roleId);
		List<Integer> myJnIds = duiwuToMyJnIds(duiwu);
		myJnIds.remove(myJnId);
		jnListToDuiwu(myJnIds, duiwu);
		accountService.updateDuiwu(duiwu);
		myJianniangService.jnSetWar(myJnId, 0);
		return Constant.SUCCESS;
	}
	
	private void jnListToDuiwu(List<Integer> jnIds, Duiwu duiwu) {
		duiwu.setOneId(null);
		duiwu.setTwoId(null);
		duiwu.setThreeId(null);
		duiwu.setFourId(null);
		duiwu.setFiveId(null);
		duiwu.setSixId(null);
		duiwu.setCount(jnIds.size());
		switch (jnIds.size()) {
		case 6:
			duiwu.setSixId(jnIds.get(5));
		case 5:
			duiwu.setFiveId(jnIds.get(4));
		case 4:
			duiwu.setFourId(jnIds.get(3));
		case 3:
			duiwu.setThreeId(jnIds.get(2));
		case 2:
			duiwu.setTwoId(jnIds.get(1));
		case 1:
			duiwu.setOneId(jnIds.get(0));
			break;
		default:
			break;
		}
	}

}
