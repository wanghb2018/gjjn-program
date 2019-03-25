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
import com.mochen.dao.JianniangSJMapper;
import com.mochen.dao.JunxianMapper;
import com.mochen.dao.KeyanMapper;
import com.mochen.dao.MyJianniangMapper;
import com.mochen.model.Duiwu;
import com.mochen.model.Jianniang;
import com.mochen.model.JianniangSJ;
import com.mochen.model.Junxian;
import com.mochen.model.Keyan;
import com.mochen.model.MyJianniang;
import com.mochen.model.Role;
import com.mochen.service.data.DuiwuData;
import com.mochen.utils.Constant;

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
	@Autowired
	JianniangSJMapper jianniangSJMapper;

	public void create(Duiwu duiwu) {
		duiwuMapper.insertSelective(duiwu);
	}
	
	public Duiwu getDuiwuByRoleId(Integer roleId) {
		return duiwuMapper.getByRoleId(roleId);
	}
	
	public List<Junxian> getAllJunxian(){
		return junxianMapper.getAll();
	}

	public DuiwuData reCalJNZdl(Role role) {
		Duiwu duiwu = duiwuMapper.getByRoleId(role.getId());
		List<Integer> myJnIds = duiwuToMyJnIds(duiwu);
		if (CollectionUtils.isEmpty(myJnIds)) {
			return new DuiwuData();
		}
		List<MyJianniang> myJns = myJianniangMapper.getByIdList(myJnIds);
		List<Jianniang> jns = new ArrayList<>();
		for (MyJianniang myJn : myJns) {
			jns.add(jianniangMapper.selectByPrimaryKey(myJn.getJnId()));
		}
		Keyan keyan = keyanMapper.getByRoleId(role.getId());
		Junxian junxian = junxianMapper.selectByPrimaryKey(role.getJunxianId());
		double jxRate = (double)junxian.getPowerrate() / 100 + 1;
		for (int i = 0; i< myJns.size();i++) {
			myJns.get(i).calShuxing(jns.get(i), keyan, jxRate);
		}
		myJianniangMapper.batchUpdate(myJns);
		DuiwuData data = new DuiwuData(myJns);
		duiwu.setCount(myJns.size());
		duiwu.setTotalzdl(data.getZdl());
		duiwuMapper.updateByPrimaryKey(duiwu);
		return data;

	}

	private List<Integer> duiwuToMyJnIds(Duiwu duiwu) {
		return Arrays.asList(duiwu.getOneId(), duiwu.getTwoId(), duiwu.getThreeId(), duiwu.getFourId(),
				duiwu.getFiveId(), duiwu.getSixId()).stream().filter(Objects::nonNull).collect(Collectors.toList());
	}
	
	public void duiwuAddJy(Role role, int jy, Duiwu duiwu) {
		if (duiwu == null) {
			duiwu = duiwuMapper.getByRoleId(role.getId());
		}
		List<Integer> myJnIds = duiwuToMyJnIds(duiwu);
		if (CollectionUtils.isEmpty(myJnIds)) {
			return;
		}
		List<MyJianniang> myJns = myJianniangMapper.getByIdList(myJnIds);
		for (MyJianniang myJn : myJns) {
			if (myJn.getLevel() < 100) {
				JianniangSJ sj = jianniangSJMapper.selectByPrimaryKey(myJn.getLevel());
				myJn.setJingyan(myJn.getJingyan() + jy);
				if (myJn.getJingyan() > sj.getNeedjy()) {
					myJn.setLevel(myJn.getLevel()+1);
					myJn.setJingyan(myJn.getJingyan()-sj.getNeedjy());
				}
			} 
		}
		myJianniangMapper.batchUpdate(myJns);
	}
	
	public Integer shangzhen(Integer roleId, Integer myJnId) {
		Duiwu duiwu = duiwuMapper.getByRoleId(roleId);
		if (duiwu.getCount() == 6) {
			return Constant.FAILED;
		}
		List<Integer> myJnIds = duiwuToMyJnIds(duiwu);
		if (myJnIds.contains(myJnId)) {
			return Constant.OTHER;
		}
		myJnIds.add(myJnId);
		jnListToDuiwu(myJnIds, duiwu);
		duiwuMapper.updateByPrimaryKey(duiwu);
		MyJianniang jn = myJianniangMapper.selectByPrimaryKey(myJnId);
		jn.setIswar(1);
		myJianniangMapper.updateByPrimaryKey(jn);
		return Constant.SUCCESS;
	}
	
	public Integer xiuxi(Integer roleId, Integer myJnId) {
		Duiwu duiwu = duiwuMapper.getByRoleId(roleId);
		List<Integer> myJnIds = duiwuToMyJnIds(duiwu);
		myJnIds.remove(myJnId);
		jnListToDuiwu(myJnIds, duiwu);
		duiwuMapper.updateByPrimaryKey(duiwu);
		MyJianniang jn = myJianniangMapper.selectByPrimaryKey(myJnId);
		jn.setIswar(0);
		myJianniangMapper.updateByPrimaryKey(jn);
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
