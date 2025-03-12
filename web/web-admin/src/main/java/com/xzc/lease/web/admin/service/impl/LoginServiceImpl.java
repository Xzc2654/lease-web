package com.xzc.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.xzc.lease.common.constant.RedisConstant;
import com.xzc.lease.common.exception.LeaseException;
import com.xzc.lease.common.result.ResultCodeEnum;
import com.xzc.lease.common.utils.JWTUtil;
import com.xzc.lease.model.entity.SystemUser;
import com.xzc.lease.model.enums.BaseStatus;
import com.xzc.lease.web.admin.mapper.SystemUserMapper;
import com.xzc.lease.web.admin.service.LoginService;
import com.xzc.lease.web.admin.vo.login.CaptchaVo;
import com.xzc.lease.web.admin.vo.login.LoginVo;
import com.xzc.lease.web.admin.vo.system.user.SystemUserInfoVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SystemUserMapper mapper;
    @Override
    public CaptchaVo getCaptcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);

        String code = specCaptcha.text().toLowerCase();
        String key = RedisConstant.ADMIN_LOGIN_PREFIX + UUID.randomUUID();
        String image = specCaptcha.toBase64();
        redisTemplate.opsForValue().set(key,code,RedisConstant.ADMIN_LOGIN_CAPTCHA_TTL_SEC, TimeUnit.SECONDS);

        return new CaptchaVo(image,key);
    }

    @Override
    public String login(LoginVo loginVo) {
        //前端发送`username`、`password`、`captchaKey`、`captchaCode`请求登录。
        //判断`captchaCode`是否为空，若为空，则直接响应`验证码为空`；若不为空进行下一步判断。
        if (loginVo.getCaptchaCode() == null) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
        }
        //根据`captchaKey`从Redis中查询之前保存的`code`，若查询出来的`code`为空，则直接响应`验证码已过期`；若不为空进行下一步判断。
        String code = redisTemplate.opsForValue().get(loginVo.getCaptchaKey());
        if (code == null){
            //验证码已过期
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
        }
        //比较`captchaCode`和`code`，若不相同，则直接响应`验证码不正确`；若相同则进行下一步判断。
        if (!loginVo.getCaptchaCode().toLowerCase().equals(code)) {
            throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
        }
        //根据`username`查询数据库，若查询结果为空，则直接响应`账号不存在`；若不为空则进行下一步判断。
//        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SystemUser::getUsername,loginVo.getUsername());
        SystemUser systemUser = mapper.selectByLoginVo(loginVo.getUsername());

        if (systemUser == null){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }
        //查看用户状态，判断是否被禁用，若禁用，则直接响应`账号被禁`；若未被禁用，则进行下一步判断
        if (systemUser.getStatus() == BaseStatus.DISABLE){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        }
        //比对`password`和数据库中查询的密码，若不一致，则直接响应`账号或密码错误`，若一致则进行入最后一步。
        if (!systemUser.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword()))){
            throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }
        //创建JWT，并响应给浏览器。
        return JWTUtil.createToken(systemUser.getId(),systemUser.getUsername());
    }

    @Override
    public SystemUserInfoVo getLoginUserinfo(Long userId) {
        SystemUserInfoVo userInfoVo = new SystemUserInfoVo();
        SystemUser systemUser = mapper.selectById(userId);
        userInfoVo.setName(systemUser.getUsername());
        userInfoVo.setAvatarUrl(systemUser.getAvatarUrl());
        return userInfoVo;
    }


}
