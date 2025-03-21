package com.xzc.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xzc.lease.model.entity.BrowsingHistory;
import com.xzc.lease.web.app.mapper.BrowsingHistoryMapper;
import com.xzc.lease.web.app.service.BrowsingHistoryService;
import com.xzc.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liubo
 * @description 针对表【browsing_history(浏览历史)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {


    @Autowired
    private BrowsingHistoryMapper browsingHistoryMapper;
    @Override
    public IPage<HistoryItemVo> pageByUserId(Page<HistoryItemVo> historyItemVoPage, Long userId) {
        return browsingHistoryMapper.pageByUserId(historyItemVoPage,userId);
    }

    @Override
    @Async
    public void saveHistory(Long userId, long id) {
        LambdaQueryWrapper<BrowsingHistory> historyWrapper = new LambdaQueryWrapper<>();
        historyWrapper.eq(BrowsingHistory::getUserId,userId).eq(BrowsingHistory::getRoomId,id);
        BrowsingHistory browsingHistory = browsingHistoryMapper.selectOne(historyWrapper);

        if (browsingHistory != null){
            browsingHistory.setBrowseTime(new Date());
            browsingHistoryMapper.updateById(browsingHistory);

        }else {
            BrowsingHistory history = new BrowsingHistory();
            history.setUserId(userId);
            history.setRoomId(id);
            history.setBrowseTime(new Date());
            browsingHistoryMapper.insert(history);
        }
    }
}




