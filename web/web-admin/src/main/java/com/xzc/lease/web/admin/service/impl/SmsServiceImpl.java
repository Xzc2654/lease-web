package com.xzc.lease.web.admin.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.xzc.lease.web.admin.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {
//    @Autowired
//    private Client client;

    @Override
    public void sendCode(String phone, String code) {
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(phone);
        sendSmsRequest.setSignName("小船租房");
        sendSmsRequest.setTemplateCode("SMS_480570002");
        sendSmsRequest.setTemplateParam("{\"code\":\"" + code + "\"}\n");

//        try {
//            client.sendSms(sendSmsRequest);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}
