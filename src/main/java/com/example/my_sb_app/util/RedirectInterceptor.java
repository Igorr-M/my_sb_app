package com.example.my_sb_app.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RedirectInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
//            String args = request.getQueryString() != null ? request.getQueryString() : "";
//            String url;
//            if (args == null) {
//                url = request.getRequestURI().toString()
//            } else {
//                url = request.getRequestURI().toString() + "?" + args;
//            }
            String url = request.getRequestURI();
            String args = request.getQueryString();

            if (args != null && !args.isEmpty()) {
                url += "?" + args;
            }
            response.setHeader("Turbolinks-Location", url);
        }
    }
}
