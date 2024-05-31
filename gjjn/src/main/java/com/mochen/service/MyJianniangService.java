package com.mochen.service;

import com.mochen.dao.MyJianniangMapper;
import com.mochen.model.Jianniang;
import com.mochen.model.MyJianniang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MyJianniangService {
    @Autowired
    MyJianniangMapper mapper;
    @Autowired
    JianniangService jianniangService;

    public List<MyJianniang> getUserJns(Integer roleId) {
        List<MyJianniang> userJns = mapper.getUserJns(roleId);
        if (userJns == null) {
            return null;
        }

        Map<Integer, Jianniang> allJnMap = jianniangService.getAllJnMap();
        userJns = userJns.stream().peek(e -> e.setJn(allJnMap.get(e.getJnId())))
                .sorted(Comparator.comparing(MyJianniang::getIswar, Comparator.reverseOrder())
                        .thenComparing(e -> e.getJn().getPinji(), Comparator.reverseOrder())
                        .thenComparing(MyJianniang::getLevel, Comparator.reverseOrder())
                        .thenComparing(MyJianniang::getId))
                .collect(Collectors.toList());
        return userJns;
    }

    public MyJianniang getJnById(Integer id) {
        MyJianniang myJn = mapper.selectByPrimaryKey(id);
        Jianniang jn = jianniangService.getById(myJn.getJnId());
        myJn.setJn(jn);
        return myJn;
    }

    public MyJianniang getJnByJnId(Integer roleId, Integer jnId) {
        MyJianniang myJn = mapper.getByJnId(roleId, jnId);
        if (myJn == null) {
            return null;
        }
        Jianniang jn = jianniangService.getById(myJn.getJnId());
        myJn.setJn(jn);
        return myJn;
    }

    public List<MyJianniang> getByIds(List<Integer> ids) {
        List<MyJianniang> myJns = mapper.getByIdList(ids);
        Map<Integer, Jianniang> allJnMap = jianniangService.getAllJnMap();
        myJns.forEach(e -> e.setJn(allJnMap.get(e.getJnId())));
        return myJns;
    }

    public MyJianniang addMyJN(Integer roleId, Jianniang jn, Integer isWar) {
        MyJianniang myJN = new MyJianniang(roleId, jn, isWar);
        myJN.calShuxing(null);
        mapper.insert(myJN);
        return myJN;
    }

    public void addMyJN(Integer roleId, Jianniang jn) {
        addMyJN(roleId, jn, 0);
    }

    public void update(MyJianniang myJn) {
        mapper.updateByPrimaryKey(myJn);
    }

    public void batchUpdate(List<MyJianniang> myJns) {
        mapper.batchUpdate(myJns);
    }

    public void jnAddjy(Integer roleId, Integer exp) {
        mapper.jnAddjy(roleId, exp);
    }

    public void jnSetWar(int id, int isWar) {
        MyJianniang jn = new MyJianniang();
        jn.setId(id);
        jn.setIswar(isWar);
        mapper.updateByPrimaryKeySelective(jn);
    }
}
