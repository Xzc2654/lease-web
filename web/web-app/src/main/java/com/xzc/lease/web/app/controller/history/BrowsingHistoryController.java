package com.xzc.lease.web.app.controller.history;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzc.lease.common.context.LoginUserContext;
import com.xzc.lease.common.result.Result;
import com.xzc.lease.web.app.service.BrowsingHistoryService;
import com.xzc.lease.web.app.vo.history.HistoryItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "浏览历史管理")
@RestController
@RequestMapping("/app/history")
public class BrowsingHistoryController {

    @Autowired
    private BrowsingHistoryService service;
    @Operation(summary = "获取浏览历史")
    @GetMapping("pageItem")
    private Result<IPage<HistoryItemVo>> page(@RequestParam long current ,@RequestParam long size){
        Page<HistoryItemVo> historyItemVoPage = new Page<>();
        Long userId = LoginUserContext.getLoginUser().getUserId();
        IPage<HistoryItemVo> result = service.pageByUserId(historyItemVoPage,userId);
        return Result.ok(result);
    }


}
