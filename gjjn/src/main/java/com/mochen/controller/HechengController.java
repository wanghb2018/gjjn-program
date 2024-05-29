package com.mochen.controller;

import com.mochen.controller.uidata.SuipianResponse;
import com.mochen.controller.uidata.UserSpResponse;
import com.mochen.model.Jianniang;
import com.mochen.model.Suipian;
import com.mochen.service.JianniangService;
import com.mochen.service.SuipianService;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class HechengController {
    @Autowired
    JianniangService jianniangService;
    @Autowired
    SuipianService suipianService;

    @GetMapping("/showHcList")
    public UserSpResponse getLoseList(@SessionAttribute(Constant.SESSION_ROLE_ID)Integer roleId) {
        List<Jianniang> jns = jianniangService.getLoseJn(roleId);
        if (CollectionUtils.isEmpty(jns)) {
            return null;
        }
        List<Integer> jnIds = jns.stream().map(Jianniang::getId).collect(Collectors.toList());
        List<Integer> needSpJnIds = new ArrayList<>(jnIds);
        needSpJnIds.add(Constant.JN_ZBL);
        needSpJnIds.add(Constant.JN_JBL);
        List<Suipian> spList = suipianService.getUserSpByJnIds(needSpJnIds, roleId);
        Map<Integer, Suipian> suipianMap = Optional.ofNullable(spList).orElse(Collections.emptyList()).stream().collect(Collectors.toMap(Suipian::getJnId, Function.identity()));
        Suipian empty = new Suipian();
        Suipian zbl = suipianMap.getOrDefault(Constant.JN_ZBL, empty);
        Suipian jbl = suipianMap.getOrDefault(Constant.JN_JBL, empty);
        List<SuipianResponse> spResponses = new ArrayList<>();
        for (Jianniang jn : jns) {
            Suipian suipian = suipianMap.getOrDefault(jn.getId(), empty);
            int num = suipian.getNum();
            if (num > 0) {
                if (jn.getPinji() < 3) {
                    num += zbl.getNum();
                } else if (jn.getPinji() < 5) {
                    num += jbl.getNum();
                }
            }
            SuipianResponse spRep = new SuipianResponse(jn, num);
            spResponses.add(spRep);
        }
        UserSpResponse response = new UserSpResponse();
        response.setZbl(zbl.getNum());
        response.setJbl(jbl.getNum());
        response.setSps(spResponses.stream().sorted(Comparator.comparing(SuipianResponse::getPinji, Comparator.reverseOrder())
                .thenComparing(SuipianResponse::getJnId)).collect(Collectors.toList()));
        return response;
    }

    @GetMapping("/jnHecheng")
    public Integer jnHecheng(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId, Integer jnId) {
        return suipianService.combineJN(jnId, roleId);
    }
}
