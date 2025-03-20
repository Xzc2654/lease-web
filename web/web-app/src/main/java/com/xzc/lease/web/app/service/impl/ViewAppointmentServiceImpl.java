package com.xzc.lease.web.app.service.impl;

import com.xzc.lease.model.entity.ViewAppointment;
import com.xzc.lease.web.app.mapper.ViewAppointmentMapper;
import com.xzc.lease.web.app.service.ApartmentInfoService;
import com.xzc.lease.web.app.service.ViewAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzc.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.xzc.lease.web.app.vo.apartment.ApartmentItemVo;
import com.xzc.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.xzc.lease.web.app.vo.appointment.AppointmentItemVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {


    @Autowired
    private ViewAppointmentMapper mapper;
    @Autowired
    private ApartmentInfoService apartmentInfoService;

    @Override
    public List<AppointmentItemVo> listAppointmentItemByUserId(Long userId) {
        return mapper.listAppointmentItemByUserId(userId);
    }

    @Override
    public AppointmentDetailVo getAppointmentDetailVoById(Long id) {
        AppointmentDetailVo appointmentDetailVo = new AppointmentDetailVo();
        //用id取得预约信息
        ViewAppointment viewAppointment = mapper.selectById(id);
        //BeanUtils转移属性
        BeanUtils.copyProperties(viewAppointment,appointmentDetailVo);
        //取得公寓id
        Long apartmentId = viewAppointment.getApartmentId();
        //获取公寓基本信息
        ApartmentDetailVo apartmentDetailById = apartmentInfoService.getApartmentDetailById(apartmentId);
        ApartmentItemVo apartmentItemVo = new ApartmentItemVo();
        BeanUtils.copyProperties(apartmentDetailById,apartmentItemVo);

        appointmentDetailVo.setApartmentItemVo(apartmentItemVo);
        return appointmentDetailVo;
    }
}




