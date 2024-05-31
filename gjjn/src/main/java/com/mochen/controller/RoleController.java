package com.mochen.controller;

import com.mochen.model.*;
import com.mochen.service.AccountService;
import com.mochen.service.DuiwuService;
import com.mochen.service.JianniangService;
import com.mochen.service.MyJianniangService;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class RoleController {
    @Autowired
    JianniangService jianniangService;
    @Autowired
    AccountService accountService;
    @Autowired
    MyJianniangService myJianniangService;
    @Autowired
    DuiwuService duiwuService;

    @PostMapping("/createRole")
    public Role createRole(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId, String jnImgId, String roleName,
                           HttpSession session) {
        Jianniang jn = jianniangService.getById(Constant.InitialJN.getIdByImg(jnImgId));
        Role role = new Role();
        role.setUserId(userId);
        role.setRolename(roleName);
        role.setTouxiang(jn.getTouxiang());
        role.setGuajitime(new Date());
        accountService.createRole(role);
        MyJianniang myjn = myJianniangService.addMyJN(role.getId(), jn, 1);
        Jianniang afu = jianniangService.getById(262); // 登录送阿芙
        myJianniangService.addMyJN(role.getId(), afu);
        Duiwu duiwu = new Duiwu(role.getId(), myjn);
        duiwuService.create(duiwu);
        Keyan keyan = new Keyan(role.getId());
        accountService.createKeyan(keyan);
        session.setAttribute(Constant.SESSION_ROLE_ID, role.getId());
        jianniangService.spBatchSave(initBl(role.getId()));
        return role;
    }

    private List<Suipian> initBl(Integer roleId) {
        Suipian zbl = new Suipian();
        zbl.setJnId(1);
        zbl.setRoleId(roleId);
        Suipian jbl = new Suipian();
        jbl.setJnId(2);
        jbl.setRoleId(roleId);
        return Arrays.asList(zbl, jbl);
    }

    @GetMapping("/getRoleInfo")
    public Role getRoleInfo(@SessionAttribute(Constant.SESSION_USER_ID) Integer userId) {
        return accountService.getByUserId(userId);
    }
}
