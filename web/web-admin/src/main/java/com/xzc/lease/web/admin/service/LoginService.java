package com.xzc.lease.web.admin.service;

import com.xzc.lease.web.admin.vo.login.CaptchaVo;
import com.xzc.lease.web.admin.vo.login.LoginVo;
import com.xzc.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {

    CaptchaVo getCaptcha();

    String login(LoginVo loginVo);
}
