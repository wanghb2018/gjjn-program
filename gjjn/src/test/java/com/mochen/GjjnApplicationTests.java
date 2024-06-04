package com.mochen;

import com.mochen.dao.JianniangMapper;
import com.mochen.model.Jianniang;
import com.mochen.model.MyJianniang;
import com.mochen.service.AccountService;
import com.mochen.service.DuiwuService;
import com.mochen.service.JianniangService;
import com.mochen.service.MyJianniangService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GjjnApplicationTests {
	@Autowired
	AccountService accountService;
	@Autowired
	MyJianniangService myJianniangService;
	@Autowired
	DuiwuService duiwuService;
	@Autowired
	JianniangService jianniangService;
	@Autowired
	JianniangMapper jianniangMapper;

	@Test
	public void contextLoads() {
		List<Integer> roleIds = Arrays.asList(55,57,61,62,64,65,70,71,74,86,89,92,94,101,109,110,112,121,122,123,124,125,136,138,144,145,146,148,152,156,160,161,163,166,167,170,171,173,174,175,181,187,204,205,209,223,225,240,242,244,245,248,254,256,257,258,271,274,380,383,384,385,392,395,396,398,400,405,409,410,417,420,1056,1060);
		for (Integer roleId : roleIds) {
			System.out.println(roleId);
			List<MyJianniang> userJns = myJianniangService.getUserJns(roleId);
			if (CollectionUtils.isEmpty(userJns)) {
				continue;
			}
			for (MyJianniang userJn : userJns) {
				if (userJn.getIswar() == 0) {
					userJn.calShuxing(null);
				}
			}
			myJianniangService.batchUpdate(userJns);
			duiwuService.reCalDwZdl(roleId);
		}
	}

	@Test
	public void addJn() throws FileNotFoundException {
		int startId = 3100;
		Map<String, Jianniang> jnMap = jianniangService.getAllJn().stream().collect(Collectors.toMap(Jianniang::getName, Function.identity()));
		File dir = new File("D:\\blhx");
		for (File item : dir.listFiles()) {
			System.out.println("begin:" + item.getName());
			File infoFile = new File(item, "info.txt");
			BufferedReader br = new BufferedReader(new FileReader(infoFile));

			List<String> lines = br.lines().collect(Collectors.toList());
			if (jnMap.containsKey(lines.get(0))) {
				Jianniang jianniang = jnMap.get(lines.get(0));
				jianniang.setTouxiang(lines.get(2));
				jianniang.setLihui(lines.get(3));
				jianniangMapper.updateByPrimaryKey(jianniang);
				System.out.println("更新：" + jianniang.getName());
			} else {
				Jianniang jn = new Jianniang();
				jn.setId(startId++);
				jn.setName(lines.get(0));
				jn.setPinji(Integer.parseInt(lines.get(1)));
				jn.setTouxiang(lines.get(2));
				jn.setLihui(lines.get(3));
				jn.setColor(getColor(jn.getPinji()));

				int[] arr = ThreadLocalRandom.current().ints(4, 0, 16).toArray();
				jn.setGongji(90 + arr[0] + jn.getPinji() * 5);
				jn.setFangyu(30 + arr[1] + jn.getPinji() * 5 );
				jn.setXueliang(100 + + arr[2] + jn.getPinji() * 5);
				jn.setSudu(15 + + arr[3] + jn.getPinji() * 5);
				int baojiBase = (jn.getPinji() > 2 ? jn.getPinji() - 2 : 0) * 10;
				if (baojiBase > 0) {
					jn.setBaoji(baojiBase - ThreadLocalRandom.current().nextInt(6));
					jn.setDuobi(baojiBase / 2 - ThreadLocalRandom.current().nextInt(6));
				} else {
					jn.setBaoji(0);
					jn.setDuobi(0);
				}
				jn.setStar((jn.getPinji() + 1) / 2 + 1);
				int spnum = 25 + jn.getPinji() * 25;
				if (jn.getPinji() == 6) {
					spnum = 200;
				}
				jn.setSpnum(spnum);
				jianniangService.addNewJn(jn);
				System.out.println("新增：" + jn.getName());
			}

		}

	}

	private String getColor(int pinji) {
		String c = "white";
		switch (pinji) {
			case 0:
				c = "white";
				break;
			case 1:
				c = "cyan";
				break;
			case 2:
				c = "violet";
				break;
			case 3:
				c = "gold";
				break;
			case 4:
				c = "orange";
				break;
			case 5:
				c = "deeppink";
				break;
			case 6:
				c = "red";
				break;
		}
		return c;
	}
}
