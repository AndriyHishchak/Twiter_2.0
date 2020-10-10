package com.Spring.SpringLetsCode.Util;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView !=null) {
         String args = request.getQueryString() !=null ? request.getQueryString() : "";//page=2&size=0
         String url = request.getRequestURI().toString() + "?" + args; ///main?page=2&size=0
         response.setHeader("Turbolinks-Location",url);
        }
    }
}
