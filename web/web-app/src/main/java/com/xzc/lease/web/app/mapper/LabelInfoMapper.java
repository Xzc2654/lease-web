package com.xzc.lease.web.app.mapper;

import com.xzc.lease.model.entity.LabelInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liubo
* @description 针对表【label_info(标签信息表)】的数据库操作Mapper
* @createDate 2023-07-26 11:12:39
* @Entity com.xzc.lease.model.entity.LabelInfo
*/
public interface LabelInfoMapper extends BaseMapper<LabelInfo> {


    List<LabelInfo> selectByApartId(Long id);

    List<LabelInfo> selectByRoomId(long id);

}




