package com.xzc.lease.web.admin.mapper;

import com.xzc.lease.model.entity.DistrictInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author liubo
* @description 针对表【district_info】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.xzc.lease.model.DistrictInfo
*/
public interface DistrictInfoMapper extends BaseMapper<DistrictInfo> {

    String selectPNameByID(Long districtId);
}




