package com.mochen.service;

import com.mochen.model.MyJianniang;
import com.mochen.model.Role;
import com.mochen.model.Suipian;
import com.mochen.service.data.DuiwuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AsyncJobService {
	@Autowired
	JianniangService jianniangService;
	@Autowired
	DuiwuService duiwuService;
	@Autowired
	AccountService accountService;
	@Autowired
	MyJianniangService myJianniangService;

	@Async("myThreadPool")
	public void mapBossAsyncJob(List<Suipian> sps, DuiwuInfo duiwu, Integer jnJy, Role role, Integer zhgjy, Integer wuzi, Boolean flag) {
		jianniangService.spBatchSave(sps);
		Map<Integer, Integer> myjnLevelMap = duiwu.getMyJns().stream().collect(Collectors.toMap(MyJianniang::getId, MyJianniang::getLevel));
		duiwuService.duiwuAddJy(role.getId(), jnJy);
		accountService.roleAddJy(role.getId(), zhgjy, wuzi, duiwu.getMyJns().size(), flag);
		tryReCalZdl(myjnLevelMap, role.getId());
	}

	public void tryReCalZdl(Map<Integer, Integer> myjnLevelMap, int roleId) {
		myJianniangService.getByIds(new ArrayList<>(myjnLevelMap.keySet())).forEach(e -> {
			Integer level = myjnLevelMap.get(e.getId());
			if (!Objects.equals(e.getLevel(), level)) {
				duiwuService.reCalDwZdl(roleId);
			}
		});
	}
}
