package com.xzc.lease.web.app.service;

import com.xzc.lease.model.entity.ApartmentInfo;
import com.xzc.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {

    ApartmentDetailVo getApartmentDetailById(Long id);
}
