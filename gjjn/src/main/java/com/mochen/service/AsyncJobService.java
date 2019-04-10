package com.mochen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mochen.model.Duiwu;
import com.mochen.model.Role;
import com.mochen.model.Suipian;

@Service
public class AsyncJobService {
	@Autowired
	JianniangService jianniangService;
	@Autowired
	DuiwuService duiwuService;
	@Autowired
	AccountService accountService;
	
	@Async
	public void mapBossAsyncJob(List<Suipian> sps, Duiwu duiwu, Integer jnJy, Role role, Integer zhgjy, Integer wuzi, Boolean flag) {
		duiwuService.duiwuAddJy(role.getId(), jnJy);
		accountService.roleAddJy(role.getId(), zhgjy, wuzi, duiwu.getCount(), flag);
		jianniangService.spBatchSave(sps);
	}
}
