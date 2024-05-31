package com.mochen.controller;

import com.mochen.controller.uidata.GenericResult;
import com.mochen.controller.uidata.QiandaoData;
import com.mochen.controller.uidata.SuipianResponse;
import com.mochen.model.Jianniang;
import com.mochen.model.PhbInfo;
import com.mochen.model.Role;
import com.mochen.model.Suipian;
import com.mochen.service.AccountService;
import com.mochen.service.JianniangService;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RestController
public class MenuController {
    @Autowired
    AccountService accountService;
    @Autowired
    JianniangService jianniangService;

    @GetMapping("/jianzao")
    public GenericResult<SuipianResponse> jianzao(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId) {
        GenericResult<SuipianResponse> result = new GenericResult<>();
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
        result.setData(new SuipianResponse(sp));
        return result;
    }

    @GetMapping("/gjJianzao")
    public GenericResult<SuipianResponse> gjJianzao(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId) {
        GenericResult<SuipianResponse> result = new GenericResult<>();
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
        result.setData(new SuipianResponse(sp));
        return result;
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
        data.setSps(sps.stream().map(SuipianResponse::new).collect(Collectors.toList()));
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
}
