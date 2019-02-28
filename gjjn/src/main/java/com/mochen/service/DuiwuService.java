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
import com.mochen.dao.JianniangMapper;
import com.mochen.dao.JunxianMapper;
import com.mochen.dao.KeyanMapper;
import com.mochen.dao.MyJianniangMapper;
import com.mochen.model.Duiwu;
import com.mochen.model.JianniangWithBLOBs;
import com.mochen.model.Junxian;
import com.mochen.model.Keyan;
import com.mochen.model.MyJianniang;
import com.mochen.model.Role;
import com.mochen.service.data.DuiwuData;

@Service
public class DuiwuService {
	@Autowired
	DuiwuMapper duiwuMapper;
	@Autowired
	MyJianniangMapper myJianniangMapper;
	@Autowired
	JianniangMapper jianniangMapper;
	@Autowired
	JunxianMapper junxianMapper;
	@Autowired
	KeyanMapper keyanMapper;

	public void create(Duiwu duiwu) {
		duiwuMapper.insertSelective(duiwu);
	}

	public DuiwuData reCalJNZdl(Role role) {
		Duiwu duiwu = duiwuMapper.getByRoleId(role.getId());
		List<Integer> myJnIds = duiwuToMyJnIds(duiwu);
		if (CollectionUtils.isEmpty(myJnIds)) {
			return null;
		}
		List<MyJianniang> myJns = myJianniangMapper.getByIdList(myJnIds);
		List<JianniangWithBLOBs> jns = new ArrayList<>();
		for (MyJianniang myJn : myJns) {
			jns.add(jianniangMapper.selectByPrimaryKey(myJn.getJnId()));
		}
		Keyan keyan = keyanMapper.getByRoleId(role.getId());
		Junxian junxian = junxianMapper.selectByPrimaryKey(role.getJunxianId());
		double jxRate = (double)junxian.getPowerrate() / 100 + 1;
		for (int i = 0; i< myJns.size();i++) {
			myJns.get(i).calShuxing(jns.get(i), keyan, jxRate);
		}
		myJianniangMapper.batchSave(myJns);
		DuiwuData data = new DuiwuData(myJns, jns);
		duiwu.setCount(myJns.size());
		duiwu.setTotalzdl(data.getZdl());
		duiwuMapper.updateByPrimaryKey(duiwu);
		return data;

	}

	private List<Integer> duiwuToMyJnIds(Duiwu duiwu) {
		return Arrays.asList(duiwu.getOneId(), duiwu.getTwoId(), duiwu.getThreeId(), duiwu.getFourId(),
				duiwu.getFiveId(), duiwu.getSixId()).stream().filter(Objects::nonNull).collect(Collectors.toList());
	}


}
