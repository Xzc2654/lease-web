package com.xzc.lease.common.interceptor;

import com.xzc.lease.common.context.LoginUser;
import com.xzc.lease.common.context.LoginUserContext;
import com.xzc.lease.common.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("access_token");
        Claims claims = JWTUtil.parseToken(token);
        Long userId = claims.get("userId", Long.class);
        String username = claims.get("username", String.class);
        LoginUserContext.setLoginUser(new LoginUser(userId, username));
        return true;
    }
}
