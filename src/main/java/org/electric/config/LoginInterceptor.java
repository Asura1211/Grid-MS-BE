package org.electric.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)throws Exception {
        //如果没有登录，响应admin
        if(request.getSession().getAttribute("userid")==null){
            response.sendRedirect("/admin/index");
            return false;
        }
        return true;
    }
}
