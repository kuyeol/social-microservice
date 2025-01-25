package org.acme.ext.terran.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GlobalRedirectAdvice implements HandlerInterceptor
{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        // 특정 경로 제외 조건
        if (!path.startsWith("/excluded")
            && !path.equals("/target-page")
            && !path.startsWith("/swagger-ui")) { // Swagger 경로 제외
            response.sendRedirect("/target-page");
            return false; // 요청 처리 중단
        }
        return true; // 요청 계속 처리
    }
}