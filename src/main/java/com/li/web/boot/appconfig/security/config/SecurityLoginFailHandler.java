package com.li.web.boot.appconfig.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SecurityLoginFailHandler implements AuthenticationFailureHandler {

    private String springSecurityContextKey = "SPRING_SECURITY_CONTEXT";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("-----这是失败处理-----");
        Map<String, Object> map = new HashMap<>();


        if (request.getSession()!=null){
            SecurityContext security = (SecurityContext) request.getSession().getAttribute(springSecurityContextKey);
            if (security!=null){
                security.getAuthentication().getDetails();
            }
        }
        map.put("status", 401);
        if (e instanceof LockedException) {
            map.put("msg", "账户被锁定，登录失败！");
        } else if (e instanceof BadCredentialsException) {
            map.put("msg", "用户名或密码输入错误，登录失败！");
        } else if (e instanceof DisabledException) {
            map.put("msg", "账户被禁用，登录失败！");
        } else if (e instanceof AccountExpiredException) {
            map.put("msg", "账户过期，登录失败！");
        } else if (e instanceof CredentialsExpiredException) {
            map.put("msg", "密码过期，登录失败！");
        } else {
            map.put("msg", "登录失败！");
        }
        log.info("---失败原因:"+map.get("msg"));

    }
}
