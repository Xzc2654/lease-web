package com.xzc.lease.web.app.service.impl;

import com.xzc.lease.model.entity.ApartmentInfo;
import com.xzc.lease.model.entity.FacilityInfo;
import com.xzc.lease.model.entity.LabelInfo;
import com.xzc.lease.model.enums.ItemType;
import com.xzc.lease.web.app.mapper.*;
import com.xzc.lease.web.app.service.ApartmentInfoService;
import com.xzc.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.xzc.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {


}




