package com.xzc.lease.web.app.service;

import com.xzc.lease.model.entity.LeaseAgreement;
import com.xzc.lease.model.entity.LeaseTerm;
import com.xzc.lease.model.entity.PaymentType;
import com.xzc.lease.web.app.vo.agreement.AgreementDetailVo;
import com.xzc.lease.web.app.vo.agreement.AgreementItemVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {

    List<AgreementItemVo> listAgreementItemByPhone(String username);

    AgreementDetailVo getAgreementDetailById(Long id);
    

    List<LeaseTerm> LeaseTermlistByRoomId(Long id);

    List<PaymentType> listByRoomId(Long id);
}
