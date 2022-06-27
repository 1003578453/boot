package com.li.web.boot.filter;


import com.li.web.boot.util.SpringBootApplicationUtil;
import com.li.web.boot.util.UrlHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

        if (session==null){
            log.info(randomId+"用户登录状态:未登录");
        }else {
            SecurityContext context = (SecurityContext) session.getAttribute(this.springSecurityContextKey);
            Authentication authentication = context.getAuthentication();
            log.info(randomId+"用户登录状态:已登录");
            log.info(randomId+"用户登录信息:"+authentication);
        }

        HttpSecurity httpSecurity = SpringBootApplicationUtil.getApplicationContext().getBean(HttpSecurity.class);
        SpringBootApplicationUtil.getApplicationContext().getBean(GlobalMethodSecurityConfiguration.class);
        //httpSecurity.ge
        System.out.println(httpSecurity);

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        log.info("-----------UrlAuthFilter销毁-------------");
    }
}
