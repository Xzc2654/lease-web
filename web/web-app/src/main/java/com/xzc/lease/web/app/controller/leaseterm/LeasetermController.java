package com.xzc.lease.web.app.controller.leaseterm;

import com.xzc.lease.common.result.Result;
import com.xzc.lease.model.entity.LeaseTerm;
import com.xzc.lease.web.app.service.LeaseAgreementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "租期信息")
@RequestMapping("/app/term")
public class LeasetermController {
    @Autowired
    LeaseAgreementService service;

    @GetMapping("listByRoomId")
    @Operation(summary = "根据房间id获取可选获取租期列表")
    public Result<List<LeaseTerm>> listByRoomId(@RequestParam Long id) {
        List<LeaseTerm> list = service.LeaseTermlistByRoomId(id);
        return Result.ok(list);
    }
}
