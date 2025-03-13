package com.xzc.lease.web.app.service.impl;

import com.xzc.lease.common.context.LoginUserContext;
import com.xzc.lease.model.entity.*;
import com.xzc.lease.model.enums.ItemType;
import com.xzc.lease.model.enums.LeaseStatus;
import com.xzc.lease.web.app.mapper.*;
import com.xzc.lease.web.app.service.BrowsingHistoryService;
import com.xzc.lease.web.app.service.RoomInfoService;
import com.xzc.lease.web.app.vo.apartment.ApartmentItemVo;
import com.xzc.lease.web.app.vo.attr.AttrValueVo;
import com.xzc.lease.web.app.vo.fee.FeeValueVo;
import com.xzc.lease.web.app.vo.graph.GraphVo;
import com.xzc.lease.web.app.vo.room.RoomDetailVo;
import com.xzc.lease.web.app.vo.room.RoomItemVo;
import com.xzc.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {


}




