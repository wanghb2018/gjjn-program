package com.mochen.dao;

import com.mochen.model.NoticeConfirmed;
import org.apache.ibatis.annotations.Param;

public interface NoticeConfirmedMapper {
    NoticeConfirmed getRoleConfirmed(@Param("roleId") int roleId, @Param("noticeId") int noticeId);

    void insert(NoticeConfirmed data);
}
