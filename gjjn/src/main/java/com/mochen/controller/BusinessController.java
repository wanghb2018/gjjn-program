package com.mochen.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.mochen.controller.uidata.GenericResult;
import com.mochen.controller.uidata.MapBossData;
import com.mochen.controller.uidata.MapGuajiData;
import com.mochen.controller.uidata.MyJnInfoUIData;
import com.mochen.model.Duiwu;
import com.mochen.model.GameMap;
import com.mochen.model.Jianniang;
import com.mochen.model.JianniangMaps;
import com.mochen.model.JianniangSJ;
import com.mochen.model.Junxian;
import com.mochen.model.Keyan;
import com.mochen.model.MyJianniang;
import com.mochen.model.Role;
import com.mochen.model.RoleSJ;
import com.mochen.model.Suipian;
import com.mochen.service.AccountService;
import com.mochen.service.DuiwuService;
import com.mochen.service.GameMapService;
import com.mochen.service.JianniangService;
import com.mochen.service.data.DuiwuData;
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

	@GetMapping("/allRoleSJ")
	public List<RoleSJ> getAllRoleSj() {
		return accountService.getAllRoleSJ();
	}

	@GetMapping("/allGameMap")
	public List<GameMap> getAllGameMap() {
		return gameMapService.getAllGameMap();
	}

	@GetMapping("/allJunxian")
	public List<Junxian> getAllJunxian() {
		return duiwuService.getAllJunxian();
	}

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
		MyJianniang myjn = jianniangService.addMyJN(role.getId(), jn, 1);
		Jianniang afu = jianniangService.getById(262); // 登录送阿芙
		jianniangService.addMyJN(role.getId(), afu);
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
	public DuiwuData getDuiwuInfo(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId) {
		Role role = accountService.getByUserId(userId);
		return duiwuService.reCalJNZdl(role);
	}

	@GetMapping("/showJnList")
	public List<MyJianniang> showJnList(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId) {
		return jianniangService.getUserJns(roleId);
	}

	@GetMapping("/showSpList")
	public List<Suipian> showSpList(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId) {
		return jianniangService.getUserSps(roleId);
	}

	@GetMapping("/showJnInfo")
	public MyJnInfoUIData showJnInfo(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId, Integer jnId) {
		MyJianniang myJn = jianniangService.getMyJnById(jnId);
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
		List<Suipian> sps = new ArrayList<Suipian>();

		int c = 0;
		Random random = new Random();
		for (int i = 0; i < rewardCount; i++) {
			if (random.nextInt(300) == 250) {
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
				int index = random.nextInt(length);
				int jnId = jnMaps.get(index).getJnId();
				jnCount.put(jnId, jnCount.get(jnId) + 1);
			}
			for (Map.Entry<Integer, Integer> entry : jnCount.entrySet()) {
				Jianniang jn = jianniangService.getById(entry.getKey());
				sps.add(new Suipian(role.getId(), jn, entry.getValue()));
			}
			if (!sps.isEmpty()) {
				jianniangService.spBatchSave(sps);
			}
		}

		GameMap gameMap = gameMapService.getGameMapById(role.getGuajimapId());
		int wz = gameMap.getWz() * rewardCount;
		int jy = gameMap.getJnjy() * rewardCount;
		duiwuService.duiwuAddJy(role, jy);
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
		Random random = new Random();
		if ((double) (random.nextInt(200) + 900) / 1000 * duiwu.getTotalzdl() <= map.getZdl()) {
			result.setHr(Constant.FAILED);
			return result;
		}
		boolean flag = false;
		if (map.getId() == role.getOpenmapId() && map.getId() < 48) {
			result.setHr(Constant.OTHER);
			flag = true;
		}
		MapBossData data = new MapBossData();
		data.setGuajiId(role.getGuajimapId());
		data.setOpenId(role.getOpenmapId() + 1);
		data.setWz(map.getWz() * 10 * duiwu.getCount());
		data.setJnjy(map.getJnjy() * 10 * duiwu.getCount());
		data.setZhgjy(map.getZhgjy() * duiwu.getCount());
		List<JianniangMaps> jnList = gameMapService.getByMapId(role.getGuajimapId());
		List<Suipian> sps = new ArrayList<>();
		random.ints(0, jnList.size()).limit(duiwu.getCount()).forEach(e -> {
			Jianniang jn = jianniangService.getById(jnList.get(e).getJnId());
			sps.add(new Suipian(role.getId(), jn, 1));
		});
		data.setSps(sps);
		jianniangService.spBatchSave(sps);
		duiwuService.duiwuAddJy(role, data.getJnjy());
		accountService.roleAddJy(role, data.getZhgjy(), data.getWz(), duiwu.getCount(), flag);
		result.setData(data);
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
		RoleSJ roleSj = accountService.getRoleSJById(role.getLevel());
		if (role.getWuzi() < roleSj.getNeedwz()) {
			return Constant.OTHER;
		}
		role.setWuzi(role.getWuzi() - roleSj.getNeedwz());
		role.setDjsx(role.getDjsx() + 10);
		role.setJunxianId(role.getJunxianId() + 1);
		accountService.updateRole(role);
		return Constant.SUCCESS;
	}
	
	@GetMapping("/jnHecheng")
	public Integer jnHecheng(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId, Integer id) {
		List<Suipian> sps = jianniangService.getUserSpsById(id, roleId);
		Suipian sp = sps.get(2);
		MyJianniang myJn = jianniangService.getByJnId(roleId, sp.getJnId());
		if (myJn != null) {
			return Constant.OTHER;
		}
		Jianniang jn = jianniangService.getById(sp.getJnId());
		int index = sp.getPinji() < 3 ? 0 : 1;
		int totalNum = sp.getNum() + sps.get(index).getNum();
		if (totalNum < sp.getSpnum()) {
			return Constant.FAILED;
		}
		if (sp.getNum() >= sp.getSpnum()) {
			sps.get(2).setNum(sp.getNum() - sp.getSpnum());
			jianniangService.addMyJN(roleId, jn);
		} else {
			sps.get(index).setNum(totalNum - sp.getSpnum());
			sps.get(2).setNum(0);
			jianniangService.addMyJN(roleId, jn);
		}
		jianniangService.spBatchUpdate(sps);
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
		role.setMofang(role.getMofang()+mfNum);
		accountService.updateRole(role);
		return mfNum;
	}

	private List<Suipian> initBl(Integer roleId) {
		Suipian zbl = new Suipian();
		zbl.setJnId(1);
		zbl.setRoleId(roleId);
		zbl.setName("泛用型布里");
		zbl.setPinji(9);
		zbl.setTouxiang("http://p0.qhimg.com/t017856449a5a277e5e.jpg");
		zbl.setColor("violet");
		zbl.setSpnum(75);
		Suipian jbl = new Suipian();
		jbl.setJnId(2);
		jbl.setRoleId(roleId);
		jbl.setName("试作型布里MKII");
		jbl.setPinji(8);
		jbl.setTouxiang("http://p4.qhimg.com/t01ec7743170cae9e68.jpg");
		jbl.setColor("gold");
		jbl.setSpnum(100);
		return Arrays.asList(zbl, jbl);
	}
}
