package com.mochen;

import com.mochen.model.MyJianniang;
import com.mochen.service.AccountService;
import com.mochen.service.DuiwuService;
import com.mochen.service.MyJianniangService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GjjnApplicationTests {
	@Autowired
	AccountService accountService;
	@Autowired
	MyJianniangService myJianniangService;
	@Autowired
	DuiwuService duiwuService;

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

}
