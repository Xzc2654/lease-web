package com.xzc.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xzc.lease.model.entity.*;
import com.xzc.lease.model.enums.ItemType;
import com.xzc.lease.web.app.mapper.*;
import com.xzc.lease.web.app.service.GraphInfoService;
import com.xzc.lease.web.app.service.LeaseAgreementService;
import com.xzc.lease.web.app.vo.agreement.AgreementDetailVo;
import com.xzc.lease.web.app.vo.agreement.AgreementItemVo;
import com.xzc.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {


    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;


    @Override
    public List<AgreementItemVo> listAgreementItemByPhone(String username) {
//        LambdaQueryWrapper<LeaseAgreement> leaseAgreementLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        leaseAgreementLambdaQueryWrapper.eq(LeaseAgreement::getPhone,username);
//        List<LeaseAgreement> leaseAgreements = leaseAgreementMapper.selectList(leaseAgreementLambdaQueryWrapper);
//
//        new ArrayList<L>()
//        AgreementItemVo agreementItemVo = new AgreementItemVo();
//        BeanUtils.copyProperties(leaseAgreements,agreementItemVo);

        return leaseAgreementMapper.listAgreementItemByPhone(username);
    }

    @Override
    public AgreementDetailVo getAgreementDetailById(Long id) {
        //1.查询租约信息
        LeaseAgreement leaseAgreement = leaseAgreementMapper.selectById(id);
        if (leaseAgreement == null) {
            return null;
        }

        //2.查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(leaseAgreement.getApartmentId());

        //3.查询房间信息
        RoomInfo roomInfo = roomInfoMapper.selectById(leaseAgreement.getRoomId());

        //4.查询公寓图片
        List<GraphVo> apartmentGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, leaseAgreement.getApartmentId());

        //5.查询房间图片
        List<GraphVo> roomGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, leaseAgreement.getRoomId());

        //6.查询租期信息
        LeaseTerm leaseTerm = leaseTermMapper.selectById(leaseAgreement.getLeaseTermId());

        //7.查询支付方式
        PaymentType paymentType = paymentTypeMapper.selectById(leaseAgreement.getPaymentTypeId());

        AgreementDetailVo agreementDetailVo = new AgreementDetailVo();
        BeanUtils.copyProperties(leaseAgreement, agreementDetailVo);
        agreementDetailVo.setApartmentName(apartmentInfo.getName());
        agreementDetailVo.setRoomNumber(roomInfo.getRoomNumber());
        agreementDetailVo.setApartmentGraphVoList(apartmentGraphVoList);
        agreementDetailVo.setRoomGraphVoList(roomGraphVoList);
        agreementDetailVo.setPaymentTypeName(paymentType.getName());
        agreementDetailVo.setLeaseTermMonthCount(leaseTerm.getMonthCount());
        agreementDetailVo.setLeaseTermUnit(leaseTerm.getUnit());

        return agreementDetailVo;
    }

    @Override
    public List<PaymentType> listByRoomId(Long id) {
        return paymentTypeMapper.selectByRoomId(id);
    }

    @Override
    public List<LeaseTerm> LeaseTermlistByRoomId(Long id) {
        return leaseTermMapper.selectByRoomId(id);
    }
}




