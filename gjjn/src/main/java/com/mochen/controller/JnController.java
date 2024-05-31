package com.mochen.controller;

import com.mochen.controller.uidata.MyJNResponse;
import com.mochen.controller.uidata.MyJnInfoUIData;
import com.mochen.model.*;
import com.mochen.service.*;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class JnController {
    @Autowired
    MyJianniangService myJianniangService;
    @Autowired
    JianniangService jianniangService;
    @Autowired
    AccountService accountService;
    @Autowired
    SuipianService suipianService;
    @Autowired
    DuiwuService duiwuService;

    @GetMapping("/showJnList")
    public List<MyJNResponse> showJnList(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId) {
        List<MyJianniang> userJns = myJianniangService.getUserJns(roleId);
        if (userJns == null) {
            return null;
        }
        return userJns.stream().map(MyJNResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/showJnInfo")
    public MyJnInfoUIData showJnInfo(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId, Integer myJnId) {
        MyJianniang myJn = myJianniangService.getJnById(myJnId);
        JianniangSJ jnSj = jianniangService.getJnsjById(myJn.getLevel());
        List<Integer> jnIds = new ArrayList<>();
        jnIds.add(myJn.getJnId());
        if (myJn.getJn().getPinji() < 3) {
            jnIds.add(Constant.JN_ZBL);
        } else if (myJn.getJn().getPinji() < 6) {
            jnIds.add(Constant.JN_JBL);
        }
        List<Suipian> suipians = suipianService.getUserSpByJnIds(jnIds, roleId);
        MyJnInfoUIData resultData = new MyJnInfoUIData(myJn, jnSj);
        for (Suipian suipian : suipians) {
            if (suipian.getJnId().equals(myJn.getJnId())) {
                resultData.setNum(suipian.getNum());
            } else if (suipian.getJnId().equals(Constant.JN_ZBL)) {
                resultData.setZblNum(suipian.getNum());
            } else if (suipian.getJnId().equals(Constant.JN_JBL)) {
                resultData.setJblNum(suipian.getNum());
            }
        }
        return resultData;
    }

    @GetMapping("/jnShengxing")
    public GenericJsonResult<String> jnShengxing(@SessionAttribute(Constant.SESSION_ROLE_ID)Integer roleId, Integer myJnId) {
        GenericJsonResult<String> result = new GenericJsonResult<>();
        Role role = accountService.getRoleById(roleId);
        MyJianniang myJn = myJianniangService.getJnById(myJnId);
        Jianniang jn = myJn.getJn();
        if (myJn.getStar() - jn.getStar() >= 3 && jn.getPinji() < 6) {
            result.setHr(Constant.OTHER);
            result.setData("已达到最大星级！");
            return result;
        }
        if (myJn.getStar() >= 100) {
            result.setHr(Constant.OTHER);
            result.setData("已达到最大星级！");
            return result;
        }
        JianniangSX jnSx = jianniangService.getJnSXbyId(myJn.getStar());
        if (role.getWuzi() < jnSx.getNeedwz()) {
            result.setHr(Constant.FAILED);
            result.setData("物资不足，缺少：" + (jnSx.getNeedwz() - role.getWuzi()));
            return result;
        }
        List<Suipian> sps = suipianService.getUserSpByJnIds(Arrays.asList(jn.getId(), Constant.JN_JBL, Constant.JN_ZBL), role.getId());
        Map<Integer, Suipian> suipianMap = Optional.ofNullable(sps).orElse(Collections.emptyList()).stream().collect(Collectors.toMap(Suipian::getJnId, Function.identity()));
        Suipian empty = new Suipian();
        Suipian jnsp = suipianMap.getOrDefault(jn.getId(), empty);
        Suipian bl = empty;
        if (jn.getPinji() < 3) {
            bl = suipianMap.getOrDefault(Constant.JN_ZBL, empty);
        } else if (jn.getPinji() < 6) {
            bl = suipianMap.getOrDefault(Constant.JN_JBL, empty);
        }

        if (jnsp.getNum() >= jn.getSpnum()) {
            jnsp.setNum(jnsp.getNum() - jn.getSpnum());
        } else {
            if (jnsp.getNum() + bl.getNum() < jn.getSpnum()) {
                result.setHr(Constant.OTHER2);
                result.setData("碎片不足，缺少：" + (jn.getSpnum() - jnsp.getNum() - bl.getNum()));
                return result;
            }
            bl.setNum(bl.getNum() - (jn.getSpnum() - jnsp.getNum()));
            jnsp.setNum(0);
        }
        myJn.setStar(myJn.getStar() + 1);
        myJn.calShuxing(null);
        myJianniangService.update(myJn);
        jianniangService.spBatchUpdate(sps);
        role.setWuzi(role.getWuzi() - jnSx.getNeedwz());
        accountService.updateRole(role);
        if (myJn.getIswar() == 1) {
            duiwuService.reCalDwZdl(roleId);
        }
        result.setHr(Constant.SUCCESS);
        result.setData("升星成功！");
        return result;
    }
}
