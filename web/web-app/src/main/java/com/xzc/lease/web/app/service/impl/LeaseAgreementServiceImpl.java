package com.xzc.lease.web.app.service.impl;

import com.xzc.lease.model.entity.*;
import com.xzc.lease.model.enums.ItemType;
import com.xzc.lease.web.app.mapper.*;
import com.xzc.lease.web.app.service.LeaseAgreementService;
import com.xzc.lease.web.app.vo.agreement.AgreementDetailVo;
import com.xzc.lease.web.app.vo.agreement.AgreementItemVo;
import com.xzc.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {


}




