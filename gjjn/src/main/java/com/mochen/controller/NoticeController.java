package com.mochen.controller;

import com.mochen.model.Notice;
import com.mochen.model.NoticeConfirmed;
import com.mochen.service.NoticeConfirmedService;
import com.mochen.service.NoticeService;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@RestController
public class NoticeController {
    @Autowired
    NoticeService noticeService;
    @Autowired
    NoticeConfirmedService noticeConfirmedService;

    @GetMapping("/getLatestNotice")
    public Notice getLatestNotice(@SessionAttribute(Constant.SESSION_ROLE_ID)Integer roleId) {
        Notice latest = noticeService.getLatest();
        if (latest == null) {
            return null;
        }
        NoticeConfirmed roleConfirmed = noticeConfirmedService.getRoleConfirmed(roleId, latest.getId());
        if (roleConfirmed != null) {
            return null;
        }
        return latest;
    }

    @PostMapping("/confirmNotice")
    public Integer confirmNotice(@SessionAttribute(Constant.SESSION_ROLE_ID)Integer roleId, Integer noticeId) {
        NoticeConfirmed roleConfirmed = noticeConfirmedService.getRoleConfirmed(roleId, noticeId);
        if (roleConfirmed != null) {
            return Constant.FAILED;
        }
        NoticeConfirmed data = new NoticeConfirmed();
        data.setRoleId(roleId);
        data.setNoticeId(noticeId);
        noticeConfirmedService.insert(data);
        return Constant.SUCCESS;
    }

    @GetMapping("/getNoticeList")
    public List<Notice> getNoticeList() {
        return noticeService.getAll();
    }
}
