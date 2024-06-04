package com.mochen.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.mochen.controller.uidata.*;
import com.mochen.model.*;
import com.mochen.service.*;
import com.mochen.service.data.DuiwuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.mochen.utils.Constant;

@RestController
public class BusinessController {
	@Autowired
	GameMapService gameMapService;
	@Autowired
	AccountService accountService;
	@Autowired
	JianniangService jianniangService;
	@Autowired
	DuiwuService duiwuService;
	@Autowired
	AsyncJobService asyncJobService;
	@Autowired
	RoleSJService roleSJService;
	@Autowired
	KeyanSJService keyanSJService;
	@Autowired
	MyJianniangService myJianniangService;
	@Autowired
	SuipianService suipianService;
    @Autowired
    private KeyanService keyanService;

	@GetMapping("/mapJiesuan")
	public GenericResult<MapGuajiData> mapJiesuan(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId,
			Integer id) {
		GenericResult<MapGuajiData> result = new GenericResult<>();
		Role role = accountService.getByUserId(userId);
		Date now = new Date();
		int guajiSecond = (int) ((now.getTime() - role.getGuajitime().getTime()) / 1000);
		int rewardCount = guajiSecond / 5;
		if (rewardCount == 0) {
			result.setHr(Constant.FAILED);
			return result;
		}
		List<Suipian> sps = new ArrayList<>();
		int c = 0;
		for (int i = 0; i < rewardCount; i++) {
			if (ThreadLocalRandom.current().nextInt(300) == 250) {
				c++;
			}
		}
		if (c > 0) {
			List<JianniangMaps> jnMaps = gameMapService.getByMapId(role.getGuajimapId());
			int length = jnMaps.size();
			int circle = c / length;
			int yu = c % length;
			Map<Integer, Integer> jnCount = jnMaps.stream()
					.collect(Collectors.toMap(JianniangMaps::getJnId, e -> circle));
			for (int j = 0; j < yu; j++) {
				int index = ThreadLocalRandom.current().nextInt(length);
				int jnId = jnMaps.get(index).getJnId();
				jnCount.put(jnId, jnCount.get(jnId) + 1);
			}
			for (Map.Entry<Integer, Integer> entry : jnCount.entrySet()) {
				Jianniang jn = jianniangService.getById(entry.getKey());
				if (entry.getValue() > 0) {
					sps.add(new Suipian(role.getId(), jn, entry.getValue()));
				}
			}
			if (!sps.isEmpty()) {
				jianniangService.spBatchSave(sps);
			}
		}

		GameMap gameMap = gameMapService.getGameMapById(role.getGuajimapId());
		int wz = gameMap.getWz() * rewardCount;
		int jy = gameMap.getJnjy() * rewardCount;
		duiwuService.duiwuAddJy(role.getId(), jy);
		role.setGuajimapId(id);
		role.setGuajitime(now);
		role.setWuzi(role.getWuzi() + wz);
		accountService.updateRole(role);
		result.setData(new MapGuajiData(sps.stream().map(SuipianResponse::new).collect(Collectors.toList()), jy, wz, guajiSecond, now));
		return result;
	}

	@GetMapping("/mapBoss")
	public GenericResult<MapBossData> mapBoss(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId, Integer id) {
		GenericResult<MapBossData> result = new GenericResult<>();
		Role role = accountService.getByUserId(userId);
		GameMap map = gameMapService.getGameMapById(id);
		DuiwuInfo duiwu = duiwuService.getDuiwuInfo(role.getId());
		int count = duiwu.getMyJns().size();
		if (role.getShiyou() < count) {
			result.setHr(-2);
			return result;
		}
		if ((double) (ThreadLocalRandom.current().nextInt(200) + 900) / 1000 * duiwu.getZdl() <= map.getZdl()) {
			result.setHr(Constant.FAILED);
			return result;
		}
		if (accountService.updateRoleShiyouByBoss(role.getId(), count) == 0) {
			result.setHr(-2);
			return result;
		}
		boolean flag = false;
		List<GameMap> allGameMap = gameMapService.getAllGameMap();
		if (map.getId().equals(role.getOpenmapId()) && map.getId() < allGameMap.size()) {
			result.setHr(Constant.OTHER);
			flag = true;
		}
		MapBossData data = new MapBossData();
		data.setGuajiId(role.getGuajimapId());
		data.setOpenId(role.getOpenmapId() + 1);
		data.setWz(map.getWz() * 10 * count);
		data.setJnjy(map.getJnjy() * 10 * count);
		data.setZhgjy(map.getZhgjy() * count);
		List<JianniangMaps> jnList = gameMapService.getByMapId(id);
		List<Suipian> sps = new ArrayList<>();
		ThreadLocalRandom.current().ints(0, jnList.size()).limit(count).forEach(e -> {
			Jianniang jn = jianniangService.getById(jnList.get(e).getJnId());
			sps.add(new Suipian(role.getId(), jn, 1));
		});
		data.setSps(sps.stream().map(SuipianResponse::new).collect(Collectors.toList()));
		result.setData(data);
		asyncJobService.mapBossAsyncJob(sps, duiwu, data.getJnjy(), role, data.getZhgjy(), data.getWz(), flag);
		return result;
	}

	@GetMapping("/jinjie")
	public Integer jinjie(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId) {
		Role role = accountService.getByUserId(userId);
		if (role.getLevel() % 10 != 0 || !role.getLevel().equals(role.getDjsx())) {
			return Constant.FAILED;
		}
		RoleSJ roleSj = roleSJService.getById(role.getLevel());
		if (role.getWuzi() < roleSj.getNeedwz()) {
			return Constant.OTHER;
		}
		role.setWuzi(role.getWuzi() - roleSj.getNeedwz());
		role.setDjsx(role.getDjsx() + 10);
		role.setJunxianId(role.getJunxianId() + 1);
		role.setLevel(role.getLevel() + 1);
		role.setExp(role.getExp() - roleSj.getNeedjy());
		accountService.updateRole(role);
		duiwuService.reCalDwZdl(role.getId());
		return Constant.SUCCESS;
	}

	@GetMapping("/saleSuipian")
	public Integer saleSuipian(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId, Integer id, Integer num) {
		Suipian sp = jianniangService.getSpById(id);
		Role role = accountService.getByUserId(userId);
		int mfNum = 0;
		if (num > 0) {
			if (sp.getNum() > num) {
				sp.setNum(sp.getNum() - num);
				mfNum = num * (sp.getPinji() + 1);
			}
		} else if (num == -1) {
			mfNum = sp.getNum() * (sp.getPinji() + 1);
			sp.setNum(0);
		}
		jianniangService.spBatchUpdate(Arrays.asList(sp));
		role.setMofang(role.getMofang() + mfNum);
		accountService.updateRole(role);
		return mfNum;
	}

	@GetMapping("/saleSuipianExist")
	public Integer saleSuipianExist(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId) {
		int n = jianniangService.saleSuipianExist(roleId);
		accountService.updateRoleByChangeDetail(roleId, null, null, n, null, null);
		return n;
	}

	@GetMapping("/saleSuipianFull")
	public Integer saleSuipianFull(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId) {
		int n = jianniangService.saleSuipianFull(roleId);
		accountService.updateRoleByChangeDetail(roleId, null, null, n, null, null);
		return n;
	}


	@GetMapping("/changeTouxiang")
	public String changeTouxiang(@SessionAttribute(Constant.SESSION_USER_ID)Integer userId, Integer id) {
		Role role = accountService.getByUserId(userId);
		MyJianniang myJn = myJianniangService.getJnById(id);
		role.setTouxiang(myJn.getJn().getTouxiang());
		accountService.updateRole(role);
		return role.getTouxiang();
	}
	
	@GetMapping("/zuanshisd")
	public Integer zuanshisd(@SessionAttribute(Constant.SESSION_USER_ID)Integer userId) {
		Role role = accountService.getByUserId(userId);
		if (role.getZuanshi() < 50) {
			return Constant.FAILED;
		}
		int zuanshi = role.getZuanshi();
		int count = 0;
		do {
			zuanshi = zuanshi - 50;
			count++;
		} while (zuanshi >= 50);

		accountService.updateRoleByChangeDetail(role.getId(), -50 * count, 500 * count, null, null, null);
		return Constant.SUCCESS;
	}
	
	@GetMapping("/saleMofang")
	public Integer saleMofang(@SessionAttribute(Constant.SESSION_USER_ID)Integer userId) {
		Role role = accountService.getByUserId(userId);
		if (role.getMofang() < 1000) {
			return Constant.FAILED;
		}
		accountService.updateRoleByChangeDetail(role.getId(), null, null, -1000, 160000, null);
		return Constant.SUCCESS;
	}
	
	@GetMapping("/buyMofang")
	public Integer buyMofang(@SessionAttribute(Constant.SESSION_USER_ID)Integer userId) {
		Role role = accountService.getByUserId(userId);
		if (role.getWuzi() < 200000) {
			return Constant.FAILED;
		}
		accountService.updateRoleByChangeDetail(role.getId(), null, null, 1000, -200000, null);
		return Constant.SUCCESS;
	}
	
	@GetMapping("/saleZbl")
	public Integer saleZbl(@SessionAttribute(Constant.SESSION_ROLE_ID)Integer roleId) {
		List<Suipian> bls = jianniangService.getRoleBl(roleId);
		if (bls.get(0).getNum() < 20) {
			return Constant.FAILED;
		}
		bls.get(0).setNum(bls.get(0).getNum() - 20);
		bls.get(1).setNum(bls.get(1).getNum() + 1);
		jianniangService.spBatchUpdate(bls);
		return Constant.SUCCESS;
	}
	
	@GetMapping("/dhZbl")
	public Integer dhZbl(@SessionAttribute(Constant.SESSION_USER_ID)Integer userId) {
		Role role = accountService.getByUserId(userId);
		if (role.getMofang() < 200) {
			return Constant.FAILED;
		}
		accountService.updateRoleByChangeDetail(role.getId(), null, null, -200, null, null);
		Jianniang zbl = jianniangService.getById(1);
		Suipian sp = new Suipian(role.getId(), zbl, 1);
		jianniangService.spBatchSave(Arrays.asList(sp));
		return Constant.SUCCESS;
	}
	
	@GetMapping("/keyanData")
	public Keyan getKeyanData(@SessionAttribute(Constant.SESSION_ROLE_ID)Integer roleId) {
		return accountService.getKeyanByRoleId(roleId);
	}
	
	@GetMapping("/keyanSj")
	public GenericJsonResult<String> keyanSj(@SessionAttribute(Constant.SESSION_USER_ID)Integer userId, String type) {
		GenericJsonResult<String> result = new GenericJsonResult<>();
		Role role = accountService.getByUserId(userId);
		Keyan keyan = accountService.getKeyanByRoleId(role.getId());
		KeyanSJ sj = null;
		switch (type) {
		case "gj":
			if (keyan.getGjdj() >= 50) {
				result.setHr(Constant.OTHER);
				result.setMsg("已达等级上限！");
				return result;
			}
			sj = keyanSJService.getById(keyan.getGjdj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				result.setHr(Constant.FAILED);
				result.setMsg("所需资源不足！");
				return result;
			}
			keyan.setGjdj(keyan.getGjdj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		case "fy":
			if (keyan.getFydj() >= 50) {
				result.setHr(Constant.OTHER);
				result.setMsg("已达等级上限！");
				return result;
			}
			sj = keyanSJService.getById(keyan.getFydj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				result.setHr(Constant.FAILED);
				result.setMsg("所需资源不足！");
				return result;
			}
			keyan.setFydj(keyan.getFydj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		case "xl":
			if (keyan.getXldj() >= 50) {
				result.setHr(Constant.OTHER);
				result.setMsg("已达等级上限！");
				return result;
			}
			sj = keyanSJService.getById(keyan.getXldj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				result.setHr(Constant.FAILED);
				result.setMsg("所需资源不足！");
				return result;
			}
			keyan.setXldj(keyan.getXldj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		case "sd":
			if (keyan.getSddj() >= 50) {
				result.setHr(Constant.OTHER);
				result.setMsg("已达等级上限！");
				return result;
			}
			sj = keyanSJService.getById(keyan.getSddj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				result.setHr(Constant.FAILED);
				result.setMsg("所需资源不足！");
				return result;
			}
			keyan.setSddj(keyan.getSddj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		case "bj":
			if (keyan.getBjdj() >= 50) {
				result.setHr(Constant.OTHER);
				result.setMsg("已达等级上限！");
				return result;
			}
			sj = keyanSJService.getById(keyan.getBjdj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				result.setHr(Constant.FAILED);
				result.setMsg("所需资源不足！");
				return result;
			}
			keyan.setBjdj(keyan.getBjdj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		case "db":
			if (keyan.getDbdj() >= 50) {
				result.setHr(Constant.OTHER);
				result.setData("已达等级上限！");
				return result;
			}
			sj = keyanSJService.getById(keyan.getDbdj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				result.setHr(Constant.FAILED);
				result.setData("所需资源不足！");
				return result;
			}
			keyan.setDbdj(keyan.getDbdj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		default:
			result.setHr(Constant.OTHER);
			result.setData("已达等级上限！");
			return result;
		}
		keyanService.updateKeyan(keyan);
		DuiwuInfo oldData = duiwuService.getDuiwuInfo(role.getId());
		DuiwuInfo duiwuInfo = duiwuService.reCalDwZdl(role.getId());
		result.setHr(Constant.SUCCESS);
		result.setMsg(String.format("科研升级成功！队伍战力提升：%d", (duiwuInfo.getZdl() - oldData.getZdl())));
		return result;
	}
}
