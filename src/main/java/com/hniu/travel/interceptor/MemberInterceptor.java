package com.hniu.travel.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object member = request.getSession().getAttribute("member");
        if (member == null){
            response.sendRedirect(request.getContextPath()+"/frontdesk/login");
            return false;
        }
        return true;
    }
}
