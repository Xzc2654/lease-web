package com.xzc.lease.web.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzc.lease.model.entity.RoomInfo;
import com.xzc.lease.model.enums.ReleaseStatus;
import com.xzc.lease.web.admin.vo.room.RoomDetailVo;
import com.xzc.lease.web.admin.vo.room.RoomItemVo;
import com.xzc.lease.web.admin.vo.room.RoomQueryVo;
import com.xzc.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface RoomInfoService extends IService<RoomInfo> {

    void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo);

    IPage<RoomItemVo> pageItem(Page<RoomItemVo> roomItemVoPage, RoomQueryVo queryVo);

    RoomDetailVo getDetailById(Long id);

    void removeByRoomId(Long id);

    void updateReleaseStatusById(Long id, ReleaseStatus status);
}
