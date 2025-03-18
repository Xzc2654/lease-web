package com.xzc.lease.web.app.service.impl;

import com.xzc.lease.common.context.LoginUserContext;
import com.xzc.lease.model.entity.*;
import com.xzc.lease.model.enums.ItemType;
import com.xzc.lease.model.enums.LeaseStatus;
import com.xzc.lease.web.app.mapper.*;
import com.xzc.lease.web.app.service.BrowsingHistoryService;
import com.xzc.lease.web.app.service.RoomInfoService;
import com.xzc.lease.web.app.vo.apartment.ApartmentDetailVo;
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

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private ApartmentLabelMapper apartmentLabelMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private AttrValueMapper attrValueMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private FeeValueMapper feeValueMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;

    @Override
    public IPage<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo) {

        return roomInfoMapper.pageItem(page,queryVo);
    }

    @Override
    public RoomDetailVo getDetailById(long id) {
        //获取房间信息
        RoomInfo roomInfo = roomInfoMapper.selectById(id);

        //获取公寓信息
        Long apartmentId = roomInfo.getApartmentId();
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(apartmentId);
        //获取公寓ApartmentItemVo信息
            //标签信息列表
        List<LabelInfo> labelInfoList = labelInfoMapper.selectByApartId(apartmentId);
            //获取图片信息
        List<GraphInfo> apartmentGraphInfoList = graphInfoMapper.selectByApartId(apartmentId);
            //获取最小租金
        BigDecimal minRent = roomInfoMapper.selectMinRent(apartmentId);
        ApartmentItemVo apartmentItemVo = new ApartmentItemVo();
        apartmentItemVo.setLabelInfoList(labelInfoList);
        apartmentItemVo.setGraphVoList(apartmentGraphInfoList);
        apartmentItemVo.setMinRent(minRent);
        BeanUtils.copyProperties(apartmentInfo,apartmentItemVo);

        //获取房间列表
        //回去房间图片列表
        List<GraphVo> roomGraphInfoList = graphInfoMapper.selectListByRommId(id);
        //获取房间属性列表
        List<AttrValueVo> attrValueVoList = attrValueMapper.selectByRoomId(id);
        //获取房间配套信息列表
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectByRoomId(id);
        //获取房间标签信息列表
        List<LabelInfo> RoomlabelInfoList = labelInfoMapper.selectByRoomId(id);
        //获取支付方式列表
        List<PaymentType> paymentTypeList = paymentTypeMapper.selectByRoomId(id);
        //获取杂费类别
        List<FeeValueVo> feeValueVoList = feeValueMapper.selectByApartId(apartmentId);
        //1获取租期列表
        List<LeaseTerm> leaseTermList = leaseTermMapper.selectByRoomId(id);
        //是否入住
        LambdaQueryWrapper<LeaseAgreement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LeaseAgreement::getRoomId, roomInfo.getId());
        queryWrapper.in(LeaseAgreement::getStatus, LeaseStatus.SIGNED, LeaseStatus.WITHDRAWING);
        Long singedCount = leaseAgreementMapper.selectCount(queryWrapper);

        RoomDetailVo roomDetailVo = new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo,roomDetailVo);
        roomDetailVo.setIsCheckIn(singedCount > 0);
        roomDetailVo.setIsDelete(roomInfo.getIsDeleted() == 1);


        roomDetailVo.setApartmentItemVo(apartmentItemVo);
        roomDetailVo.setGraphVoList(roomGraphInfoList);
        roomDetailVo.setAttrValueVoList(attrValueVoList);
        roomDetailVo.setFacilityInfoList(facilityInfoList);
        roomDetailVo.setLabelInfoList(labelInfoList);
        roomDetailVo.setPaymentTypeList(paymentTypeList);
        roomDetailVo.setFeeValueVoList(feeValueVoList);
        roomDetailVo.setLeaseTermList(leaseTermList);

        return roomDetailVo;
    }

    @Override
    public IPage<RoomItemVo> getItemByApartmentId(Page<RoomItemVo> page, long id) {
        return roomInfoMapper.getItemByApartmentId(page,id);
    }
}




