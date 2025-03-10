package com.xzc.lease.web.admin.controller.lease;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzc.lease.common.result.Result;
import com.xzc.lease.model.entity.ViewAppointment;
import com.xzc.lease.model.enums.AppointmentStatus;
import com.xzc.lease.web.admin.service.ViewAppointmentService;
import com.xzc.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.xzc.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "预约看房管理")
@RequestMapping("/admin/appointment")
@RestController
public class ViewAppointmentController {

    @Autowired
    private ViewAppointmentService service;

    @Operation(summary = "分页查询预约信息")
    @GetMapping("page")
    public Result<IPage<AppointmentVo>> page(@RequestParam long current, @RequestParam long size, AppointmentQueryVo queryVo) {
        Page<AppointmentVo> voPage = new Page<>(current,size);
        IPage<AppointmentVo> page = service.pageView(voPage,queryVo);
        return Result.ok(page);
    }

    @Operation(summary = "根据id更新预约状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam AppointmentStatus status) {
        LambdaUpdateWrapper<ViewAppointment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ViewAppointment::getId,id);
        updateWrapper.set(ViewAppointment::getAppointmentStatus,status);
        service.update(updateWrapper);
        return Result.ok();
    }

}
