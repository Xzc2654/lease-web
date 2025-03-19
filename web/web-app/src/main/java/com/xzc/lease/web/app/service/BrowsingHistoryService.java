package com.xzc.lease.web.app.service;

import com.xzc.lease.model.entity.BrowsingHistory;
import com.xzc.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【browsing_history(浏览历史)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface BrowsingHistoryService extends IService<BrowsingHistory> {

    IPage<HistoryItemVo> pageByUserId(Page<HistoryItemVo> historyItemVoPage, Long userId);

    void saveHistory(Long userId, long id);
}
