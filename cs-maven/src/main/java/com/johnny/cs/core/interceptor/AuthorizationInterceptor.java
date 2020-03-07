package com.johnny.cs.core.interceptor;

import com.johnny.cs.core.error.exception.ForbiddenException;
import com.johnny.cs.core.error.exception.UnAuthorizationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final String API_KEY = "ndgndg91-good";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("탓어");
        String authorization = request.getHeader("Authorization");

        if (StringUtils.isEmpty(authorization)) {
            throw new UnAuthorizationException();
        }

        if ( ! StringUtils.equals(authorization, API_KEY)) {
            throw new ForbiddenException();
        }

        return true;
    }
}
