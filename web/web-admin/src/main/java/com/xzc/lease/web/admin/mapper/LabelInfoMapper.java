package com.xzc.lease.web.admin.mapper;

import com.xzc.lease.model.entity.LabelInfo;
import com.xzc.lease.model.enums.ItemType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【label_info(标签信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.xzc.lease.model.LabelInfo
*/
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

    List<LabelInfo> selectLabelInfoById(Long id);

    List<LabelInfo> selectLabelInfoByRoomId(Long id);
}




