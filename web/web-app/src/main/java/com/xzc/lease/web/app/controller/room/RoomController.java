package com.xzc.lease.web.app.controller.room;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzc.lease.common.result.Result;
import com.xzc.lease.web.app.service.RoomInfoService;
import com.xzc.lease.web.app.vo.room.RoomDetailVo;
import com.xzc.lease.web.app.vo.room.RoomItemVo;
import com.xzc.lease.web.app.vo.room.RoomQueryVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "房间信息")
@RestController
@RequestMapping("/app/room")
public class RoomController {

    @Autowired
    RoomInfoService service;

    @Operation(summary = "分页查询房间列表")
    @GetMapping("pageItem")
    public Result<IPage<RoomItemVo>> pageItem(@RequestParam long current, @RequestParam long size, RoomQueryVo queryVo){
        Page<RoomItemVo> page = new Page<>(current, size);
        IPage<RoomItemVo> result = service.pageItem(page, queryVo);
        return Result.ok(result);
    }

    @Operation(summary = "根据id获取房间详细信息")
    @GetMapping("getDetailById")
    public Result<RoomDetailVo> getDetailById(@RequestParam long id){
        return Result.ok();
    }

    @Operation(summary = "根据公寓id分页查询房间列表")
    @GetMapping("pageItemByApartmentId")
    public Result<RoomDetailVo> getItemByApartmentId(@RequestParam long current,@RequestParam long size,@RequestParam long id){
        return Result.ok();
    }

}