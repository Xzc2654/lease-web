package com.xzc.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzc.lease.model.entity.ViewAppointment;
import com.xzc.lease.web.admin.mapper.ViewAppointmentMapper;
import com.xzc.lease.web.admin.service.ViewAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzc.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.xzc.lease.web.admin.vo.appointment.AppointmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {

    @Autowired
    private ViewAppointmentMapper viewAppointmentMapper;

    public ViewAppointmentServiceImpl(ViewAppointmentMapper viewAppointmentMapper) {
        this.viewAppointmentMapper = viewAppointmentMapper;
    }

    @Override
    public IPage<AppointmentVo> pageView(Page<AppointmentVo> voPage, AppointmentQueryVo queryVo) {
        return viewAppointmentMapper.pageView(voPage,queryVo);
    }
}




