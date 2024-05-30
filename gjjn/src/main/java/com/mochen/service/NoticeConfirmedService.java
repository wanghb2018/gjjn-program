package com.mochen.service;

import com.mochen.dao.NoticeConfirmedMapper;
import com.mochen.model.NoticeConfirmed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeConfirmedService {
    @Autowired
    NoticeConfirmedMapper mapper;

    public NoticeConfirmed getRoleConfirmed(int roleId, int noticeId) {
        return mapper.getRoleConfirmed(roleId, noticeId);
    }

    public void insert(NoticeConfirmed data) {
        mapper.insert(data);
    }
}
