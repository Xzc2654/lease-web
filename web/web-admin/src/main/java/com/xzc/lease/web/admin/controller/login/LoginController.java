package com.xzc.lease.web.admin.controller.login;


import com.xzc.lease.common.context.LoginUserContext;
import com.xzc.lease.common.result.Result;
import com.xzc.lease.web.admin.service.LoginService;
import com.xzc.lease.web.admin.vo.login.CaptchaVo;
import com.xzc.lease.web.admin.vo.login.LoginVo;
import com.xzc.lease.web.admin.vo.system.user.SystemUserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "后台管理系统登录管理")
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private LoginService service;
    @Operation(summary = "获取图形验证码")
    @GetMapping("login/captcha")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo captchaVo = service.getCaptcha();

        return Result.ok(captchaVo);
    }

    @Operation(summary = "登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String token = service.login(loginVo);
        return Result.ok(token);
    }

    @Operation(summary = "获取登陆用户个人信息")
    @GetMapping("info")
    public Result<SystemUserInfoVo> info() {
        Long userId = LoginUserContext.getLoginUser().getUserId();
        SystemUserInfoVo userInfoVo = service.getLoginUserinfo(userId);
        return Result.ok(userInfoVo);
    }
}