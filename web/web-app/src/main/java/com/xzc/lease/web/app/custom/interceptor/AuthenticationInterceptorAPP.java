package com.xzc.lease.web.app.custom.interceptor;

import com.xzc.lease.common.context.LoginUser;
import com.xzc.lease.common.context.LoginUserContext;
import com.xzc.lease.common.exception.LeaseException;
import com.xzc.lease.common.result.ResultCodeEnum;
import com.xzc.lease.common.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
@Qualifier("APPAuthenticationInterceptor")
public class AuthenticationInterceptorAPP implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("access-token");

        if (token == null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_AUTH);
        } else {
            Claims claims = JWTUtil.parseToken(token);
            Long userId = claims.get("userId", Long.class);
            String username = claims.get("username", String.class);
            LoginUserContext.setLoginUser(new LoginUser(userId, username));
        }
        return true;


    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserContext.clear();
    }
}
