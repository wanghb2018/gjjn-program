package com.mochen.controller;

import com.mochen.controller.uidata.DuiwuResponse;
import com.mochen.service.DuiwuService;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
public class DuiwuController {
    @Autowired
    DuiwuService duiwuService;

    @GetMapping("/getDuiwuInfo")
    public DuiwuResponse getDuiwuInfo(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId) {
        return new DuiwuResponse(duiwuService.getDuiwuInfo(roleId));
    }

    @GetMapping("/shangzhen")
    public Integer shangzhen(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId, Integer id) {
        return duiwuService.shangzhen(roleId, id);
    }

    @GetMapping("/xiuxi")
    public Integer xiuxi(@SessionAttribute(Constant.SESSION_ROLE_ID) Integer roleId, Integer id) {
        return duiwuService.xiuxi(roleId, id);
    }
}
