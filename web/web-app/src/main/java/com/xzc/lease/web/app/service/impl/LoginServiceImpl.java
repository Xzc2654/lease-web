package com.xzc.lease.web.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xzc.lease.common.constant.RedisConstant;
import com.xzc.lease.common.exception.LeaseException;
import com.xzc.lease.common.result.ResultCodeEnum;
import com.xzc.lease.common.utils.JWTUtil;
import com.xzc.lease.common.utils.VerifyCodeUtil;
import com.xzc.lease.model.entity.UserInfo;
import com.xzc.lease.model.enums.BaseStatus;
import com.xzc.lease.web.app.mapper.UserInfoMapper;
import com.xzc.lease.web.app.service.LoginService;
import com.xzc.lease.web.app.service.SmsService;
import com.xzc.lease.web.app.service.UserInfoService;
import com.xzc.lease.web.app.vo.user.LoginVo;
import com.xzc.lease.web.app.vo.user.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private  SmsService smsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoService userInfoService;

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
            Long expire = redisTemplate.getExpire(key,TimeUnit.SECONDS);
            if (RedisConstant.APP_LOGIN_CODE_TTL_SEC - expire < RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC){
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }
        //3.发送短信，并将验证码存入Redis
        String verifyCode = VerifyCodeUtil.getVerifyCode(6);

        smsService.sendCode(phone,verifyCode);
        redisTemplate.opsForValue().set(key,verifyCode,RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);
    }

    @Override
    public String login(LoginVo loginVo) {
        //1.判断手机号码和验证码是否为空
        if (!StringUtils.hasText(loginVo.getPhone())){
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }
        if (!StringUtils.hasText(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        }
        //2.校验验证码
        String key = RedisConstant.APP_LOGIN_PREFIX + loginVo.getPhone();
        String code = (String) redisTemplate.opsForValue().get(key);
        if (code == null){
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);
        }
        if (!code.equals(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);
        }
        //3.判断用户是否存在,不存在则注册（创建用户）
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, loginVo.getPhone());
        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setPhone(loginVo.getPhone());
            userInfo.setStatus(BaseStatus.ENABLE);
            userInfo.setNickname("用户-"+loginVo.getPhone().substring(5));
            userInfoService.save(userInfo);
        }

        //4.判断用户是否被禁
        if (userInfo.getStatus().equals(BaseStatus.DISABLE)) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        }


        return JWTUtil.createToken(userInfo.getId(),userInfo.getPhone());
    }

    @Override
    public UserInfoVo getUserInfoById(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        UserInfoVo userInfoVo = new UserInfoVo(userInfo.getNickname(), userInfo.getAvatarUrl());
        return userInfoVo;
    }


}
