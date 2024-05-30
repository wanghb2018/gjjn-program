package com.mochen.service;

import com.mochen.dao.NoticeMapper;
import com.mochen.model.Notice;
import com.mochen.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    @Cacheable(value = Constant.CACHE_YEAR, key = Constant.CACHE_ALL_NOTICE)
    public List<Notice> getAll() {
        List<Notice> all = noticeMapper.getAll();
        if (all == null) {
            return null;
        }
        return all.stream().sorted(Comparator.comparing(Notice::getCreatedTime).reversed()).collect(Collectors.toList());
    }

    @Cacheable(value = Constant.CACHE_YEAR, key = Constant.CACHE_LATEST_NOTICE)
    public Notice getLatest() {
        List<Notice> all = noticeMapper.getAll();
        if (all == null) {
            return null;
        }
        return all.get(all.size() - 1);
    }
}
