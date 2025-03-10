package com.xzc.lease.web.admin.scheduleTasks;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xzc.lease.model.entity.LeaseAgreement;
import com.xzc.lease.model.enums.LeaseStatus;
import com.xzc.lease.web.admin.service.LeaseAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class ScheduleTask {
    @Autowired
    private  LeaseAgreementService leaseAgreementService;


    @Scheduled(cron = "0 0 0 * * *")
    public void checkInLeaseAgreementStatus(){
        LambdaUpdateWrapper<LeaseAgreement> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.le(LeaseAgreement::getLeaseEndDate,new Date());
        updateWrapper.in(LeaseAgreement::getStatus,LeaseStatus.SIGNED,LeaseStatus.WITHDRAWING);
        updateWrapper.set(LeaseAgreement::getStatus, LeaseStatus.EXPIRED);
        leaseAgreementService.update(updateWrapper);
    }
}
