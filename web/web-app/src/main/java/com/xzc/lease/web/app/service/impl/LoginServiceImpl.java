package com.xzc.lease.web.app.service.impl;


import com.xzc.lease.common.constant.RedisConstant;
import com.xzc.lease.common.exception.LeaseException;
import com.xzc.lease.common.result.ResultCodeEnum;
import com.xzc.lease.common.utils.VerifyCodeUtil;
import com.xzc.lease.web.app.service.LoginService;
import com.xzc.lease.web.app.service.SmsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {


    private final SmsService smsService;
    private final RedisTemplate<Object, Object> redisTemplate;

    public LoginServiceImpl(SmsService smsService, RedisTemplate<Object, Object> redisTemplate) {
        this.smsService = smsService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void getSMSCode(String phone) {
        //1. 检查手机号码是否为空
        if (!StringUtils.hasText(phone)){
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }
        //2. 检查Redis中是否已经存在该手机号码的key
        String key = RedisConstant.APP_LOGIN_PREFIX + phone;
        Boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey){
            Long expire = redisTemplate.getExpire(key);
            if (RedisConstant.APP_LOGIN_CODE_TTL_SEC - expire < RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC){
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }
        //3.发送短信，并将验证码存入Redis
        String verifyCode = VerifyCodeUtil.getVerifyCode(6);

        smsService.sendCode(phone,verifyCode);
        redisTemplate.opsForValue().set(key,verifyCode,RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);
    }
}
