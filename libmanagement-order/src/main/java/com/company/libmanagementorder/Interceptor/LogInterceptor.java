package com.company.libmanagementorder.Interceptor;


import com.company.libmanagementutils.entity.SecurityContextUtil;
import com.company.libmanagementutils.utils.JwtUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor implements AsyncHandlerInterceptor {
    public static final String LOGIN_TOKEN_KEY = "token";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(LOGIN_TOKEN_KEY);
        if (token == null || token.isEmpty()) {
            return false;
        }
        Integer userId = JwtUtils.getUserIdInToken(token);
        SecurityContextUtil.addUser(userId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}