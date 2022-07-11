package com.li.web.boot.filter;


import com.alibaba.fastjson.JSON;
import com.li.web.boot.util.SpringBootApplicationUtil;
import com.li.web.boot.util.UrlHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 打印当前用户登录信息,角色信息
 * 打印请求方法的权限信息
 */
@Slf4j
public class UrlAuthFilter implements Filter {

    private String springSecurityContextKey = "SPRING_SECURITY_CONTEXT";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("-----------UrlAuthFilter初始化-------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession(false);
        String randomId=httpServletRequest.toString().substring(httpServletRequest.toString().length()-8);

        //静态资源无需处理
        //静态资源无需处理
        if (UrlHelper.passStaticObject(httpServletRequest)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        HandlerMethod handlerMethod = (HandlerMethod) servletRequest.getAttribute("handlerMethod");
        if (handlerMethod!=null){
            Class<?> beanType = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            MethodSecurityMetadataSource methodSecurityMetadataSource = SpringBootApplicationUtil.getApplicationContext().getBean("methodSecurityMetadataSource",MethodSecurityMetadataSource.class);
            Collection<ConfigAttribute> attributes = methodSecurityMetadataSource.getAttributes(method, beanType);
            log.info(randomId+"用户请求接口对应权限:"+attributes);
           // log.info();
        }
        if (session==null){
            log.info(randomId+"用户登录状态:未登录");
        }else if (session.getAttribute(this.springSecurityContextKey)==null){
            log.info(randomId+"用户登录状态:未登录");
        }else {
            SecurityContext context = (SecurityContext) session.getAttribute(this.springSecurityContextKey);
            Authentication authentication = context.getAuthentication();
            log.info(randomId+"用户登录状态:已登录");
            log.info(randomId+"用户登录信息:"+ JSON.toJSONString(authentication,true));
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        log.info("-----------UrlAuthFilter销毁-------------");
    }
}
