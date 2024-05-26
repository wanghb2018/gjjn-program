package com.mochen.service;

import com.mochen.dao.SuipianMapper;
import com.mochen.model.Jianniang;
import com.mochen.model.Suipian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SuipianService {
    @Autowired
    SuipianMapper mapper;
    @Autowired
    JianniangService jianniangService;

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
}
