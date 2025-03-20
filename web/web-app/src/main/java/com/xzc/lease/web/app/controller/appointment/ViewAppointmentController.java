package com.xzc.lease.web.app.controller.appointment;

import com.xzc.lease.common.context.LoginUserContext;
import com.xzc.lease.common.result.Result;
import com.xzc.lease.model.entity.ViewAppointment;
import com.xzc.lease.web.app.service.ViewAppointmentService;
import com.xzc.lease.web.app.vo.appointment.AppointmentDetailVo;
import com.xzc.lease.web.app.vo.appointment.AppointmentItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "看房预约信息")
@RequestMapping("/app/appointment")
public class ViewAppointmentController {
    @Autowired
    private ViewAppointmentService service;

    @PostMapping("/saveOrUpdate")
    @Operation(summary = "保持或更新看房预约")
    public Result saveOrUpdate(@RequestBody ViewAppointment viewAppointment){
        viewAppointment.setUserId(LoginUserContext.getLoginUser().getUserId());
        System.out.println("************************************" + viewAppointment);
        service.saveOrUpdate(viewAppointment);
        return Result.ok();
    }

    @Operation(summary = "查询个人预约看房列表")
    @GetMapping("listItem")
    public Result<List<AppointmentItemVo>> listItem() {

        List<AppointmentItemVo> list = service.listAppointmentItemByUserId(LoginUserContext.getLoginUser().getUserId());
        return Result.ok(list);
    }

    @GetMapping("getDetailById")
    @Operation(summary = "根据ID查询预约详情信息")
    public Result<AppointmentDetailVo> getDetailById(Long id) {
        AppointmentDetailVo detail = service.getAppointmentDetailVoById(id);
        return Result.ok(detail);
    }


}
