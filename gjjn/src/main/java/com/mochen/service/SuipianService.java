package com.mochen.service;

import com.mochen.dao.SuipianMapper;
import com.mochen.model.Jianniang;
import com.mochen.model.MyJianniang;
import com.mochen.model.Suipian;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SuipianService {
    @Autowired
    SuipianMapper mapper;
    @Autowired
    JianniangService jianniangService;
    @Autowired
    MyJianniangService myJianniangService;

    public List<Suipian> getUserSps(Integer roleId) {
        List<Suipian> sps = mapper.getAllUserSps(roleId);
        if (CollectionUtils.isEmpty(sps)) {
            return null;
        }
        Map<Integer, Jianniang> allJnMap = jianniangService.getAllJnMap();
        sps = sps.stream().peek(e -> e.setJn(allJnMap.get(e.getJnId())))
                .sorted(Comparator.comparing(Suipian::getPinji, Comparator.reverseOrder())
                        .thenComparing(Suipian::getNum, Comparator.reverseOrder())
                        .thenComparing(Suipian::getId))
                .collect(Collectors.toList());
        return sps;
    }

    public int combineJN(int jnId, int roleId) {
        List<Suipian> suipians = mapper.getByJnIds(Arrays.asList(jnId, Constant.JN_JBL, Constant.JN_ZBL), roleId);
        if (CollectionUtils.isEmpty(suipians)) {
            return Constant.FAILED;
        }
        MyJianniang myJn = myJianniangService.getJnByJnId(roleId, jnId);
        if (myJn != null) {
            return Constant.OTHER;
        }
        Jianniang jn = jianniangService.getById(jnId);
        if (jn == null) {
            return Constant.OTHER;
        }
        Map<Integer, Suipian> suipianMap = suipians.stream().collect(Collectors.toMap(Suipian::getJnId, Function.identity()));
        Suipian jnsp = suipianMap.get(jnId);
        if (jnsp == null) {
            return Constant.OTHER;
        }
        Suipian bl =  suipianMap.getOrDefault(jn.getPinji() < 3 ? Constant.JN_ZBL : Constant.JN_JBL, new Suipian());
        int total = jnsp.getNum() + bl.getNum();
        if (total < jn.getSpnum()) {
            return Constant.FAILED;
        }
        if (jn.getSpnum() <= jnsp.getNum()) {
            jnsp.setNum(jnsp.getNum() - jn.getSpnum());
        } else {
            bl.setNum(bl.getNum() + jnsp.getNum() - jn.getSpnum());
            jnsp.setNum(0);
        }

        mapper.batchUpdate(new ArrayList<>(suipianMap.values()));
        myJianniangService.addMyJN(roleId, jn);
        return Constant.SUCCESS;
    }
}
