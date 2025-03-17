package com.xzc.lease.web.app.controller.login;

import com.xzc.lease.common.context.LoginUserContext;
import com.xzc.lease.common.result.Result;
import com.xzc.lease.model.entity.UserInfo;
import com.xzc.lease.web.app.service.LoginService;
import com.xzc.lease.web.app.vo.user.LoginVo;
import com.xzc.lease.web.app.vo.user.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "登陆管理")
@RequestMapping("/app/")
public class LoginController {

    @Autowired
    private LoginService service;

    @GetMapping("login/getCode")
    @Operation(summary = "获取短信验证码")
    public Result<?> getCode(@RequestParam String phone){
        service.getSMSCode(phone);
        return Result.ok();
    }
    @PostMapping("login")
    @Operation(summary = "登录")
    public Result<?> login(@RequestBody LoginVo loginVo){
        String token = service.login(loginVo);
        return Result.ok(token);
    }
    @GetMapping("info")
    @Operation(summary = "获取登录信息")
    public Result<UserInfoVo> info(){
        Long userId = LoginUserContext.getLoginUser().getUserId();
        UserInfoVo userInfoVo = service.getUserInfoById(userId);
        return Result.ok(userInfoVo);
    }

}
