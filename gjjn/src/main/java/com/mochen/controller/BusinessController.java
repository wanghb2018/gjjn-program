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

import javax.servlet.http.HttpSession;

import com.mochen.controller.uidata.*;
import com.mochen.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.mochen.model.Duiwu;
import com.mochen.model.GameMap;
import com.mochen.model.Jianniang;
import com.mochen.model.JianniangMaps;
import com.mochen.model.JianniangSJ;
import com.mochen.model.JianniangSX;
import com.mochen.model.Keyan;
import com.mochen.model.KeyanSJ;
import com.mochen.model.MyJianniang;
import com.mochen.model.PhbInfo;
import com.mochen.model.Role;
import com.mochen.model.RoleSJ;
import com.mochen.model.Suipian;
import com.mochen.utils.Constant;
import com.mochen.utils.Constant.InitialJN;

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

	@PostMapping("/createRole")
	public Role createRole(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId, String jnImgId, String roleName,
			HttpSession session) {
		Jianniang jn = jianniangService.getById(InitialJN.getIdByImg(jnImgId));
		Role role = new Role();
		role.setUserId(userId);
		role.setRolename(roleName);
		role.setTouxiang(jn.getTouxiang());
		role.setGuajitime(new Date());
		accountService.createRole(role);
		MyJianniang myjn = myJianniangService.addMyJN(role.getId(), jn, 1);
		Jianniang afu = jianniangService.getById(262); // 登录送阿芙
		myJianniangService.addMyJN(role.getId(), afu);
		Duiwu duiwu = new Duiwu(role.getId(), myjn);
		duiwuService.create(duiwu);
		Keyan keyan = new Keyan(role.getId());
		accountService.createKeyan(keyan);
		session.setAttribute(Constant.SESSION_ROLE_ID, role.getId());
		jianniangService.spBatchSave(initBl(role.getId()));
		return role;
	}

	@GetMapping("/getRoleInfo")
	public Role getRoleInfo(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId) {
		return accountService.getByUserId(userId);
	}

	@GetMapping("/getDuiwuInfo")
	public DuiwuResponse getDuiwuInfo(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId) {
		Role role = accountService.getByUserId(userId);
		return new DuiwuResponse(duiwuService.reCalJNZdl(role));
	}

	@GetMapping("/showJnList")
	public List<MyJNResponse> showJnList(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId) {
		List<MyJianniang> userJns = myJianniangService.getUserJns(roleId);
		if (userJns == null) {
			return null;
		}
		return userJns.stream().map(MyJNResponse::new).collect(Collectors.toList());
	}

	@GetMapping("/showSpList")
	public UserSpResponse showSpList(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId) {
		List<Suipian> sps = suipianService.getUserSps(roleId);
		return new UserSpResponse(sps);
	}

	@GetMapping("/showJnInfo")
	public MyJnInfoUIData showJnInfo(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId, Integer jnId) {
		MyJianniang myJn = myJianniangService.getJnById(jnId);
		JianniangSJ jnSj = jianniangService.getJnsjById(myJn.getLevel());
		return new MyJnInfoUIData(myJn, jnSj);
	}

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
		result.setData(new MapGuajiData(sps, jy, wz, guajiSecond, now));
		return result;
	}

	@GetMapping("/mapBoss")
	public GenericResult<MapBossData> mapBoss(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId, Integer id) {
		GenericResult<MapBossData> result = new GenericResult<>();
		Role role = accountService.getByUserId(userId);
		GameMap map = gameMapService.getGameMapById(id);
		Duiwu duiwu = duiwuService.getDuiwuByRoleId(role.getId());
		if (role.getShiyou() < duiwu.getCount()) {
			result.setHr(-2);
			return result;
		}
		if ((double) (ThreadLocalRandom.current().nextInt(200) + 900) / 1000 * duiwu.getTotalzdl() <= map.getZdl()) {
			result.setHr(Constant.FAILED);
			return result;
		}
		if (accountService.updateRoleShiyouByBoss(role.getId(), duiwu.getCount()) == 0) {
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
		data.setWz(map.getWz() * 10 * duiwu.getCount());
		data.setJnjy(map.getJnjy() * 10 * duiwu.getCount());
		data.setZhgjy(map.getZhgjy() * duiwu.getCount());
		List<JianniangMaps> jnList = gameMapService.getByMapId(id);
		List<Suipian> sps = new ArrayList<>();
		ThreadLocalRandom.current().ints(0, jnList.size()).limit(duiwu.getCount()).forEach(e -> {
			Jianniang jn = jianniangService.getById(jnList.get(e).getJnId());
			sps.add(new Suipian(role.getId(), jn, 1));
		});
		data.setSps(sps);
		result.setData(data);
		asyncJobService.mapBossAsyncJob(sps, duiwu, data.getJnjy(), role, data.getZhgjy(), data.getWz(), flag);
		return result;
	}

	@GetMapping("/shangzhen")
	public Integer shangzhen(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId, Integer id) {
		return duiwuService.shangzhen(roleId, id);
	}

	@GetMapping("/xiuxi")
	public Integer xiuxi(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId, Integer id) {
		return duiwuService.xiuxi(roleId, id);
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
		return Constant.SUCCESS;
	}

	@GetMapping("/jnHecheng")
	public Integer jnHecheng(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId, Integer jnId) {
		return suipianService.combineJN(jnId, roleId);
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

	@GetMapping("/qiandao")
	public GenericResult<QiandaoData> qiandao(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId) {
		GenericResult<QiandaoData> result = new GenericResult<>();
		Role role = accountService.getByUserId(userId);
		boolean flag = true;
		Date now = new Date();
		if (role.getQdsj() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String str1 = sdf.format(role.getQdsj());
			String str2 = sdf.format(now);
			if (str1.equals(str2)) {
				flag = false;
			}
		}
		if (!flag) {
			result.setHr(Constant.FAILED);
			return result;
		}
		QiandaoData data = new QiandaoData();
		data.setShiyou(500 + role.getQdts() * 10);
		data.setMofang(100 + role.getQdts() * 3);
		data.setZuanshi(50 + role.getQdts());
		List<Suipian> sps = addSuipian(role);
		data.setSps(sps);
		jianniangService.spBatchSave(sps);
		role.setQdts(role.getQdts() + 1);
		role.setQdsj(now);
		role.setShiyou(role.getShiyou() + data.getShiyou());
		role.setMofang(role.getMofang() + data.getMofang());
		role.setZuanshi(role.getZuanshi() + data.getZuanshi());
		accountService.updateRole(role);
		result.setData(data);
		return result;
	}

	@GetMapping("/jianzao")
	public GenericResult<Suipian> jianzao(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId) {
		GenericResult<Suipian> result = new GenericResult<>();
		Role role = accountService.getByUserId(userId);
		if (role.getMofang() < 100) {
			result.setHr(Constant.FAILED);
			return result;
		}
		if (role.getWuzi() < 1000) {
			result.setHr(Constant.OTHER);
			return result;
		}
		int updateResult = accountService.jianzaoUpdate(role.getId(), 1000, 100);
		if (updateResult == 0) {
			result.setHr(Constant.OTHER);
			return result;
		}
		int num = 50 - (int) Math.pow(ThreadLocalRandom.current().nextInt(900), 0.5);
		int rate = ThreadLocalRandom.current().nextInt(100) + 1;
		List<Jianniang> jns = null;
		if (rate <= 30) {
			jns = jianniangService.getAllJnByPinji(0);
		} else if (rate <= 65) {
			jns = jianniangService.getAllJnByPinji(1);
		} else if (rate <= 85) {
			jns = jianniangService.getAllJnByPinji(2);
		} else {
			jns = jianniangService.getAllJnByOverPinji(2);
		}
		Suipian sp = new Suipian(role.getId(), jns.get(ThreadLocalRandom.current().nextInt(jns.size())), num);
		jianniangService.spBatchSave(Arrays.asList(sp));
		result.setData(sp);
		return result;
	}

	@GetMapping("/gjJianzao")
	public GenericResult<Suipian> gjJianzao(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId) {
		GenericResult<Suipian> result = new GenericResult<>();
		Role role = accountService.getByUserId(userId);
		if (role.getMofang() < 268) {
			result.setHr(Constant.FAILED);
			return result;
		}
		if (role.getWuzi() < 3000) {
			result.setHr(Constant.OTHER);
			return result;
		}
		int updateResult = accountService.jianzaoUpdate(role.getId(), 3000, 268);
		if (updateResult == 0) {
			result.setHr(Constant.OTHER);
			return result;
		}
		int num = 100 - (int) Math.pow(ThreadLocalRandom.current().nextInt(4900), 0.5);
		int rate = ThreadLocalRandom.current().nextInt(1000) + 1;
		List<Jianniang> jns = null;
		if (rate <= 630) {
			jns = jianniangService.getAllJnByPinji(2);
		} else if (rate <= 830) {
			jns = jianniangService.getAllJnByPinji(3);
		} else if (rate <= 930) {
			jns = jianniangService.getAllJnByPinji(4);
		} else if (rate <= 980) {
			jns = jianniangService.getAllJnByPinji(5);
		} else {
			jns = jianniangService.getAllJnByOverPinji(5);
		}
		Suipian sp = new Suipian(role.getId(), jns.get(ThreadLocalRandom.current().nextInt(jns.size())), num);
		jianniangService.spBatchSave(Arrays.asList(sp));
		result.setData(sp);
		return result;
	}

	@GetMapping("/phb")
	public List<PhbInfo> getPhb(String type) {
		switch (type) {
		case "a":
			return accountService.djPhb();
		case "b":
			return accountService.zdlPhb();
		case "c":
			return accountService.tjPhb();
		case "d":
			return accountService.jnPhb();
		default:
			return null;
		}
	}
	
	@GetMapping("/getLoseJn")
	public List<Jianniang> getLoseJn(@SessionAttribute(Constant.SESSION_ROLE_ID)Integer roleId){
		return jianniangService.getLoseJn(roleId);
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
		accountService.updateRoleByChangeDetail(role.getId(), -50, 500, null, null, null);
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
	
	@GetMapping("/jnShengxing")
	public Integer jnShengxing(@SessionAttribute(Constant.SESSION_USER_ID)Integer userId, Integer id) {
		Role role = accountService.getByUserId(userId);
		MyJianniang myJn = myJianniangService.getJnById(id);
		Jianniang jn = myJn.getJn();
		if (myJn.getStar() - jn.getStar() >= 3 && jn.getPinji() < 6) {
			return Constant.OTHER;
		}
		if (myJn.getStar() >= 50) {
			return Constant.OTHER;
		}
		JianniangSX jnSx = jianniangService.getJnSXbyId(myJn.getStar());
		if (role.getWuzi() < jnSx.getNeedwz()) {
			return Constant.FAILED;
		}
		List<Suipian> sps = jianniangService.getSpByJnId(jn.getId(), role.getId());
		if (sps.size() < 3) {
			return Constant.OTHER2;
		}
		int blId = sps.get(2).getPinji() < 3 ? 0 : 1;
		Suipian jnsp = sps.get(2);
		if (jnsp.getNum() >= jn.getSpnum()) {
			sps.get(2).setNum(jnsp.getNum() - jn.getSpnum());
		} else {
			if (jnsp.getNum() + sps.get(blId).getNum() < jn.getSpnum()) {
				return Constant.OTHER2;
			}
			int remind = jnsp.getNum() + sps.get(blId).getNum() - jn.getSpnum();
			sps.get(blId).setNum(remind);
			sps.get(2).setNum(0);
		}
		myJn.setStar(myJn.getStar() + 1);
		myJn.calJNZdl(myJn.getStar() - jn.getStar() + 1);
		myJianniangService.update(myJn);
		jianniangService.spBatchUpdate(sps);
		role.setWuzi(role.getWuzi() - jnSx.getNeedwz());
		accountService.updateRole(role);
		return Constant.SUCCESS;
	}
	
	@GetMapping("/keyanData")
	public Keyan getKeyanData(@SessionAttribute(Constant.SESSION_ROLE_ID)Integer roleId) {
		return accountService.getKeyanByRoleId(roleId);
	}
	
	@GetMapping("/keyanSj")
	public Integer keyanSj(@SessionAttribute(Constant.SESSION_USER_ID)Integer userId, String type) {
		Role role = accountService.getByUserId(userId);
		Keyan keyan = accountService.getKeyanByRoleId(role.getId());
		KeyanSJ sj = null;
		switch (type) {
		case "gj":
			if (keyan.getGjdj() >= 50) {
				return Constant.OTHER;
			}
			sj = keyanSJService.getById(keyan.getGjdj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				return Constant.FAILED;
			}
			keyan.setGjdj(keyan.getGjdj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		case "fy":
			if (keyan.getFydj() >= 50) {
				return Constant.OTHER;
			}
			sj = keyanSJService.getById(keyan.getFydj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				return Constant.FAILED;
			}
			keyan.setFydj(keyan.getFydj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		case "xl":
			if (keyan.getXldj() >= 50) {
				return Constant.OTHER;
			}
			sj = keyanSJService.getById(keyan.getXldj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				return Constant.FAILED;
			}
			keyan.setXldj(keyan.getXldj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		case "sd":
			if (keyan.getSddj() >= 50) {
				return Constant.OTHER;
			}
			sj = keyanSJService.getById(keyan.getSddj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				return Constant.FAILED;
			}
			keyan.setSddj(keyan.getSddj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		case "bj":
			if (keyan.getBjdj() >= 50) {
				return Constant.OTHER;
			}
			sj = keyanSJService.getById(keyan.getBjdj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				return Constant.FAILED;
			}
			keyan.setBjdj(keyan.getBjdj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		case "db":
			if (keyan.getDbdj() >= 50) {
				return Constant.OTHER;
			}
			sj = keyanSJService.getById(keyan.getDbdj() + 1);
			if (role.getWuzi() < sj.getNeedwz() || role.getKeyandian() < sj.getNeedjy()) {
				return Constant.FAILED;
			}
			keyan.setDbdj(keyan.getDbdj() + 1);
			accountService.updateRoleByChangeDetail(role.getId(), null, null, null, -sj.getNeedwz(), -sj.getNeedjy());
			break;
		default:
			return Constant.OTHER;
		}
		accountService.updateKeyan(keyan);
		return Constant.SUCCESS;
	}

	private List<Suipian> addSuipian(Role role) {
		int count = 3;
		if (count < role.getQdts() / 4) {
			count = role.getQdts() / 4;
			if (count > 16) {
				count = 16;
			}
		}
		List<Jianniang> allJns = jianniangService.getAllJn();
		Collections.shuffle(allJns);
		return allJns.stream().limit(count)
				.map(e -> new Suipian(role.getId(), e, ThreadLocalRandom.current().nextInt(role.getQdts() + 1) + 1))
				.collect(Collectors.toList());
	}

	private List<Suipian> initBl(Integer roleId) {
		Suipian zbl = new Suipian();
		zbl.setJnId(1);
		zbl.setRoleId(roleId);
		Suipian jbl = new Suipian();
		jbl.setJnId(2);
		jbl.setRoleId(roleId);
		return Arrays.asList(zbl, jbl);
	}
}
